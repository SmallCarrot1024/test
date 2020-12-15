-ignorewarnings
##########################基础库##########################
#fastjson
-keep class com.alibaba.fastjson.** {*;}

#tnet
#-keepclasseswithmembernames class org.android.spdy.** {
#    native <methods>;
#}
-keep public class org.android.spdy.**{*;}

#mtop
-keep class anetwork.network.cache.**{*;}
-keep class com.taobao.tao.remotebusiness.**{*;}
-keep class mtopsdk.**{*;}

#network
-keep class anet.channel.**{*;}
-keep class anetwork.channel.**{*;}
##########################基础库##########################

###########################SOPHIX##########################
-keepclassmembers class com.aliyun.emas.demo.CustomApplication {
    public <init>();
}
-keep class com.aliyun.emas.demo.SophixStubApplication$RealApplicationStub
-keep class com.taobao.sophix.**{*;}
-keep class com.ta.utdid2.device.**{*;}
###########################SOPHIX##########################