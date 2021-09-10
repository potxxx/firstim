##FirstIM
>一个使用java编写的分布式即时通讯系统，包含基础的IM系统功能以及常见分布式组件的使用。

##一、业务梳理
1. 一对一聊天、群聊，消息有序、不丢失、不重复，且支持离线消息与已读未读信息查看
2. 用户注册，登录，连接，创建聊天会话，创建群聊
3. 推送、红包、朋友圈等扩展业务

##二、架构设计
1. 网关层IPConfServer，对长连接请求进行负载均衡，通过服务发现获取TCPGateWayServer的IP，指定策略返回直连IP
2. 接入层TCPGateWayServer，保持用户长连接的建立与维持，无状态多机部署，连接的建立与断开均告知RouteServer路由，业务消息均下发LogicServer进行处理
3. 路由层RouteServer，保存TCPGateWayServer与长连接uid的映射关系将其存储至缓存，并支持下行消息的转发
4. 业务逻辑层LogicServer，接受上行消息实现各项业务功能，下行消息的下发
5. 存储层DAS，对业务层提供存储接口，屏蔽底层存储逻辑
6. 服务发现组件使用Nacos，缓存组件使用redis，持久化存储使用mysql

##三、业务逻辑设计
1. 一对一聊天业务逻辑
>1.clientA向gate发送send消息---client本地为每个会话生成一个ack队列，为每个消息分配一个本地顺序递增的cid，并将其加入会话的ack队列，send后启动定时器超时重发对应的消息  
>2.gate将消息转发给logic  
>3.logic收到消息后，找到当前会话中用户已发送的最大cid，若此cid与send接受到的preid相同则接收这个send的cid，落库并为这条消息生成一个本次会话中的串行有序msg_id之后返回ack给clientA  
>4.logic转发下行消息至route，route查找缓存找到clientB连接状态，若在线则转发到clientB长连接所在的gate，若离线则流程终止  
>5.gate接受到下行消息则通知clientB来拉取新消息  
>6.clientB接收到拉取消息后，发送clientB本地的已接受到的msg_id去拉取新的消息---本次的拉取msg_id可作为上次接收信心的ack，所以设置兜底策略，clientB每五秒进行一次新消息的拉取用作拉取消息同时更新状态  
>7.logic收到拉取消息之后，将上一条状态设置为已送达，并给clientA返回已读  
>8.若clientB从离线转换为在线之后，主动拉取新的消息
2. 一对多聊天业务逻辑
>1.上行消息同一对一聊天逻辑  
>2.下行消息采用扩散写模式，一个用户的消息扩散为对多个用户的一对一消息入库，clientB拉取消息时直接拉取大于本地的msg_id消息，之后在本地进行不同会话的分离  
>3.logic对群内每个用户转发下行消息  
>4.下行消息的发送同一对一聊天逻辑

##四、存储设计
#### 用户表
id[自增ID]、user_id[用户ID]
#### 群表
id[自增ID]、group_id[群ID]、state[群状态]
#### 群用户关系表
id[自增ID]、group_id[群ID]、user_id[用户ID]
#### 推送消息表
id[自增ID]、msg_id[消息ID]、msg_from[发送者userid]、msg_to[接收者userid]、msg_type[消息类型]、group_id[群ID]、delivered[是否送达]、msg_cid[用户会话消息id]、msg_content[消息内容]

##五、技术方案设计
1. 长连接服务采用netty编写：通信协议自定义私有协议，序列化采用protobuf，协议支持客户端心跳检测与断线重连，服务端若长时间无心跳则自动断连
2. 消息有序性保证：对于上行消息client为每一个会话采用一个递增的cid，服务端接收消息进行流量整型按照cid的顺序生成递增的msg_id，对于下行消息都采用先通知后拉取的方式，拉取后在本地按照msg_id进行排序，这样就能保证接收方看到的消息顺序与发送方看到的一致，而群聊消息不同用户消息之间的顺序按照达到业务服务器的时间设定
3. 消息不丢失不重复保证：客户端维持一个ack队列未ack消息超时重发，消息发送带上本次消息的cid以及本次会话上次消息的preid，接收服务器检查preid是否与已接收到的最大cid相同，若相同则说明接收到顺序消息，若不同则判断是否已接收，返回一个服务端最大的cid
4. 负载均衡设计：TCPGateWayServer的分配采用hash一致性算法，当某一个gate宕机，客户端心跳机制会重新进行连接，当新加入一个gate服务后，控制的gate服务主动断开需要rehash的长连接并给这些连接下发重新连接的命令，同时这些客户端也会通过心跳机制进行重连
5. 消息表水平分库与索引：按照msg_to进行hash，查询频繁的功能有：查询msg_from、msg_to的最大msg_cid---建立这三个列的复合索引、查询msg_to且大于msg_id的所有消息---建立这两个列的复合索引
6. 由于分库导致msg_id不能全局递增，需要设计一个分布式递增id服务  
7. redis路由---uid，gateServerIp
##六、功能开发
* [ ] 项目架构搭建 
* [x] 私有通讯协议实现---心跳、重连、序列化、拆包解包
* [ ] 业务流程服务打通---接入层、网关层、业务层、路由层消息转发
* [ ] 存储层接口实现
* [ ] 上行消息逻辑---递增cid生成、ack队列重试、推送接口(preid沟通)
* [ ] 下行消息逻辑---拉取接口设计
* [ ] 一致性hash负载均衡长连接上线下线实现

##七、技术细节
1. 自定义协议设计
>1.协议头设计---magicCode[魔数-4字节]、type[消息类型-4字节]、bodyLength[消息体长度-4字节]---magicCode固定为0xFAFAFAFA、type 0x01[心跳] 0x02[单聊] 0x03[群聊] 0x04[推送控制消息] 0x04[拉消息]  
>2.协议体设计  
> 心跳消息 消息体为空  
> 单聊 C2CSendRequest[from、to、preId---当前发送的cid的上一个id、cid、content] C2CSendResponse[from、to、ackid---当前已落库的cid]  
> 群聊 C2GSendRequest[from,group,preId---当前发送的cid的上一个id、cid、content] C2CSendResponse[preid---当前已落库的cid]  
> 推送控制消息 消息体为空  
> 拉取消息 PullRequest[uid,msgId--本地收到的最大消息id--该id之前的消息已全接收] PullResponse[msgList消息数组--from、to、msgType、msgId、content]