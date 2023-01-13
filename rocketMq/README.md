顺序消息
https://developer.aliyun.com/article/1035440
事务消息
https://developer.aliyun.com/article/1035472


rocketmq 私信队列处理
https://blog.csdn.net/admin522043032/article/details/127303550
默认创建出来的死信队列，他里面的消息是无法读取的，在控制台和消费者中都无法读取。这是因为这些默认的死信队列，他们的权限perm被设置成了2:禁读(这个权限有三种 2:禁读，4:禁写,6:可读可写)。
需要手动将死信队列的权限配置成6，才能被消费(可以通过mqadmin指定或者web控制台)。
