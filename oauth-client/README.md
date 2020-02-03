# oauth-client
第三方应用端 客户端 模式 
初始化
app_key=82b2b2028ebe4219aa6d7e6ae8cfe2ca
app_secret=61d4bc364afcc586bde89643064757ac
流程
1、获取access_token 
http://localhost:8080/authorization-token-server/oauth/token?grant_type=client_credentials&client_secret=61d4bc364afcc586bde89643064757ac&client_id=82b2b2028ebe4219aa6d7e6ae8cfe2ca

2、调用接口 
http://localhost:8080/oauth-ucenter-provider/?access_token=bd7421027ed64fbeb94b9de509d4a43a&method=core.user.get&format=json&appkey=82b2b2028ebe4219aa6d7e6ae8cfe2ca&value=AAA&version=1.0&key=openId




















  
  
  
  