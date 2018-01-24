家策好服务
-----

**测试公众号**

> appid: wx521fd12e043115e6

> secret: 8fd8e81c5d1ca4733365e091cc5fee60

> ![img](http://image-jiacer.oss-cn-shanghai.aliyuncs.com/fbee/images/WX20171026-180834@2x.png)


**预生产公众号**

> appid: wxe2212a8922981da3

> secret: b2e875229a6b3958a903612b2d626b5c

![img](http://image-jiacer.oss-cn-shanghai.aliyuncs.com/2017101311473748620.jpeg)


**API**
微信授权连接：/api/WechatInfo/auth/url

```
 request head:
    token: #token
    uid: #userId  
    Referer: @Referer
```

**用户地址**

> 接口地址：/fbeeWebConsole_web/{domain}/api/user/address

*api*

  > 查询列表: GET    /

  > 查询详情: GET    /{addressId}

  > 新增地址: POST   /

  > 修改地址: POST   /{addressId} 

  > 设置默认: POST   /{addressId}/@default

  > 删除地址: POST /{addressId}/@delete

*memberAddressInfo*

| field | name | type | comment |
|-------|------|------|---------|
| id    | id  | int | 唯一标识|
| memberId |用户ID | int | 用户id|
|userName|用户名|string||
|type|地址类型|string|家／公司／其他|
|phone|电话|string||
|zipCode|邮编|string||
|country|国家|string|@code - #Value|
|province|省|string|@code - #Value|
|city|市|string|@code - #Value|
|district|区|string|@code - #Value|
|address|地址信息|string|街道地址|
|area|区域|string|@code - #Value|
|isUsable|是否可用|string|1是0否|
|isDefault|是否默认|string|1是0否|
|addTime|添加时间|datetime||
|addAccount|添加人|string||
|modifyTime||||
|modifyAccount||||














