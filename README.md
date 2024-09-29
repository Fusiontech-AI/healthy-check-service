### 系统概述：

体检系统是一套集信息化、智能化、高效化为一体的专业健康管理软件。它旨在为医疗机构的体检中心、专业体检机构以及企业、单位的内部体检部门等提供全面的解决方案，以提升体检服务的质量和效率，更好地满足人们日益增长的健康管理需求。
该系统涵盖了体检业务的各个环节，从预约登记到报告生成与查询，实现了全流程的数字化管理。通过与先进的医疗检测设备无缝对接，能够快速、准确地采集和传输体检数据，确保数据的真实性和可靠性。同时，系统具备强大的数据分析和处理能力，能够对体检结果进行综合评估，为受检者提供个性化的健康建议和干预方案。
### 系统模块：

```
 com.fxkc 
       
├── fxkc-ui              // 前端框架 [80]
├── fxkc-gateway         // 网关模块 [8080]
├── fxkc-auth            // 认证中心 [9200]
├── fxkc-api             // 接口模块
│       └── fxkc-api-system                          // 系统接口
├── fxkc-common          // 通用模块
│       └── fxkc-common-core                         // 核心模块
│       └── fxkc-common-datascope                    // 权限范围
│       └── fxkc-common-datasource                   // 多数据源
│       └── fxkc-common-log                          // 日志记录
│       └── fxkc-common-redis                        // 缓存服务
│       └── fxkc-common-seata                        // 分布式事务
│       └── fxkc-common-security                     // 安全模块
│       └── fxkc-common-swagger                      // 系统接口
├── fxkc-modules         // 业务模块
│       └── fxkc-system                              // 系统模块 [9201]
│       └── fxkc-gen                                 // 代码生成 [9202]
│       └── fxkc-job                                 // 定时任务 [9203]
│       └── fxkc-file                                // 文件服务 [9300]
├── fxkc-visual          // 图形化管理模块
│       └── fxkc-visual-monitor                      // 监控中心 [9100]
├──pom.xml                // 公共依赖
```



### 技术选型

## 1、系统环境

- Java EE 8
- Servlet 3.0
- Apache Maven 3
## 2、主框架
- Spring Boot 2.2.x
- Spring Framework 5.2.x
- Apache Shiro 1.7
## 3、持久层
- Apache MyBatis 3.5.x
- Hibernate Validation 6.0.x
- Alibaba Druid 1.2.x
## 4、视图层
- Bootstrap 3.3.7
- Thymeleaf 3.0.x

### 架构图：

内置功能：

```
 1.用户管理：用户是系统操作者，该功能主要完成系统用户配置。
 2.部门管理：配置系统组织机构（公司、部门、小组），树结构展现支持数据权限。
 3：岗位管理：配置系统用户所属担任职务。
 4：菜单管理：配置系统菜单，操作权限，按钮权限标识等。
 5：角色管理：角色菜单权限分配、设置角色按机构进行数据范围权限划分。
 6：字典管理：对系统中经常使用的一些较为固定的数据进行维护。
 7：参数管理：对系统动态配置常用参数。
 8：通知公告：系统通知公告信息发布维护。
 9：操作日志：系统正常操作日志记录和查询；系统异常信息日志记录和查询。
 10：登录日志：系统登录日志记录查询包含登录异常。
 11：在线用户：当前系统中活跃用户状态监控。
 12：定时任务：在线（添加、修改、删除)任务调度包含执行结果日志。
 13：代码生成：前后端代码的生成（java、html、xml、sql）支持CRUD下载 。
 14：系统接口：根据业务代码自动生成相关的api接口文档。
 15：服务监控：监视当前系统CPU、内存、磁盘、堆栈等相关信息。
 16：在线构建器：拖动表单元素生成相应的HTML代码。
 17：连接池监视：监视当前系统数据库连接池状态，可进行分析SQL找出系统性能瓶颈。
```



### 环境部署

# 准备工作
JDK >= 1.8 (推荐1.8版本)
oracle>= 11.2.0.4.0 (推荐11g 版本)
Maven >= 3.0
# 运行系统
1、前往Gitee下载页面 ，将下载解压到工作目录
2、导入到idea，菜单 File -> Import，然后选择 Maven -> Existing Maven Projects，点击 Next> 按钮，选择工作目录，然后点击 Finish 按钮，即可成功导入。
idea会自动加载Maven依赖包，初次加载会比较慢（根据自身网络情况而定）
3、创建数据库 FXKC_TJ 并导入数据脚本 sql 文件夹中的sql 文件
4、打开项目运行org.fxkc.resource.FxKcResourceApplication，出现如下图表示启动成功。

```
(♥◠‿◠)ﾉﾞ  启动成功   ლ(´ڡ`ლ)ﾞ  
 .-------.       ____     __        
 |  _ _   \      \   \   /  /    
 | ( ' )  |       \  _. /  '       
 |(_ o _) /        _( )_ .'         
 | (_,_).' __  ___(_ o _)'          
 |  |\ \  |  ||   |(_,_)'         
 |  | \ `'   /|   `-'  /           
 |  |  \    /  \      /           
 ''-'   `'-'    `-..-'  
```

  
5、打开浏览器，输入：(http://localhost:80 (opens new window)) （默认账户/密码 admin/admin123）
若能正确展示登录页面，并能成功登录，菜单及页面展示正常，则表明环境搭建成功


#必要配置
修改数据库连接，编辑resources目录下的application-druid.yml
# 数据源配置

```
spring:
    datasource:
        type: com.alibaba.druid.pool.DruidDataSource
        driverClassName: com.oracle.jdbc.Driver
        druid:
            # 主库数据源
            master:
                url: 数据库地址
                username: 数据库账号
                password: 数据库密码
修改服务器配置，编辑resources目录下的application.yml
```


# 开发环境配置
server:
  # 服务器的HTTP端口，默认为80
  port: 端口
  servlet:
    # 应用的访问路径
    context-path: /应用路径
#部署系统
打包工程文件
在ruoyi项目的bin目录下执行package.bat打包Web工程，生成war/jar包文件。
然后会在项目下生成target文件夹包含war或jar



部署工程文件
1、jar部署方式
使用命令行执行：java –jar 对应服务包名.jar

2、war部署方式
 healthy-check-service /pom.xml中的packaging修改为war，放入tomcat服务器webapps

   <packaging>war</packaging>
提示

多模块版本在ruoyi/ruoyi-admin模块下修改pom.xml

SpringBoot去除内嵌Tomcat（PS：此步骤不重要，因为不排除也能在容器中部署war）

```
<!-- 多模块排除内置tomcat -->
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-web</artifactId>
	<exclusions>
		<exclusion>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-tomcat</artifactId>
		</exclusion>
	</exclusions>
</dependency>
		
<!-- 单应用排除内置tomcat -->		
<exclusions>
	<exclusion>
		<artifactId>spring-boot-starter-tomcat</artifactId>
		<groupId>org.springframework.boot</groupId>
	</exclusion>
</exclusions>
```


#常见问题
如果使用Mac需要修改application.yml文件路径profile
如果使用Linux 提示表不存在，设置大小写敏感配置在/etc/my.cnf添加lower_case_table_names=1，重启MYSQL服务
如果提示当前权限不足，无法写入文件请检查application.yml中的profile路径或logback.xml中的log.path路径是否有可读可写操作权限

### 商业合作

- 如果您想使用功能更完善的体检系统，请联系电话：15927241173或者微信15927241173

- 如果您想基于体检系统进行定制开发，我们提供有偿定制服务支持

- 其他合作模式不限，欢迎来撩！

- 联系我们（商务请联系电话：15927241173 或者 微信15927241173）
### 关于我们

公司名称：湖北福鑫科创信息技术有限公司
地址：湖北省武汉市东湖新技术开发区关山大道泛悦城T2写字楼14层
电话：027-87705383（固话）   15927241173（移动电话）
业务合作：heyuyu@fuiontech.cn
公司主页：www.fusiontech.cn

### 
 交流咨询群

部署安装遇到问题或者想加入社区交流学习、或者是对社区有其他的想法参与等等，扫描下方二维码联系福小鑫获取帮助 

![图片1](https://github.com/user-attachments/assets/b89df7ff-fd87-4289-a6ae-597cf84581fe)


微信联系方式：15927241173
若群满，则可添加个人微信进群

![图片2](https://github.com/user-attachments/assets/3b896ffa-8280-4740-ab9a-aebc48dbc575)
