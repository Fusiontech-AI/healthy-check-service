数据源：Oracle

集成功能

1.多租户

2.分布式事务等

-- 代码结构：

基础服务：
Register：nacos注册中心

Gateway:网关

Auth：核心业务，权限等

Upms:用户管理

Common:核心包

业务模块：

knowledge:知识库模块


基础服务启动顺序：register->gateway->upms->auth->其他
