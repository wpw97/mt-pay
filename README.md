## 说明
目前仅支持微信支付

### 中间件
|中间件|版本|
|:----|:----:|
|mysql|8.0.x|
|redis|6.2.6|
### 核心依赖
| 依赖           |  版本    |
|:-------------|:-------:|
| spring boot  |  2.7.0  |
| hutool       |  5.8.3  |
| lombok       | 1.18.30 |
| Mybatis Plus |  3.5.2  |
| dynamic      |  3.5.2  |
| wechatpay    | 0.2.12  |

### 快速开始
#### 配置host

    mt-redis（redis IP或者域名）
    mt-mysql（mysql IP或者域名）

#### 微信支付
1.前期准备，参考配置类属性，在配置文件中提供相应的参数：参考微信支付官网(https://pay.weixin.qq.com/)

    配置类：com/mt/pay/config/properties/WechatPayProperty.java

    商户号：merchantId
    商户API私钥路径： privateKeyPath
    商户证书序列号：merchantSerialNumber
    商户API V3密钥：apiV3Key
    小程序id（有就提供）：appId