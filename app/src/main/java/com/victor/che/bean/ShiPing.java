package com.victor.che.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 会员卡可用服务实体
 * Author Victor
 * Email 468034043@qq.com
 * Time 2017/3/21 0021 10:51
 */
public class ShiPing implements Serializable{
    /**
     * videoList : {"pageNo":1,"pageSize":10,"count":11,"lastPage":false,"list":[{"id":"5ef5aa40a59e40bb88efe4597c00ca68","isNewRecord":false,"remarks":"","createDate":"2018-01-26 06:03:37","updateDate":"2018-01-26 14:03:37","type":null,"deviceSerial":"827899404","pondId":{"id":"245f364a06d148d09d7c22f643d79fb5","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"firmId":null,"name":"1#塘"},"firmId":{"id":"633b3a06e2e445ef9f5902e2abb23714","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"govAquFarmId":null,"firmName":"南阳方城良种场","area":null,"coordinateX":null,"coordinateY":null,"picture":null,"mainProduct":null,"comSynopsis":null},"channelNo":"5","ezopen":"ezopen://open.ys7.com/827899404/5.live","ezopenhd":"ezopen://open.ys7.com/827899404/5.hd.live","img":"|/aims/userfiles/1/files/comopinion/videoDataInformation/2018/01/WHTA3.png","videoType":null},{"id":"07c5871772e84522874c8675bbb81937","isNewRecord":false,"remarks":"","createDate":"2018-01-26 06:04:19","updateDate":"2018-01-26 14:16:15","type":null,"deviceSerial":"827899404","pondId":{"id":"af9fb38f1ae3448c9200dc7214b920a7","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"firmId":null,"name":"2#塘"},"firmId":{"id":"633b3a06e2e445ef9f5902e2abb23714","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"govAquFarmId":null,"firmName":"南阳方城良种场","area":null,"coordinateX":null,"coordinateY":null,"picture":null,"mainProduct":null,"comSynopsis":null},"channelNo":"4","ezopen":"ezopen://open.ys7.com/827899404/4.live","ezopenhd":"ezopen://open.ys7.com/827899404/4.hd.live","img":"|/aims/userfiles/1/files/comopinion/videoDataInformation/2018/01/WHTA5.png","videoType":null},{"id":"0b295510ca744e628037596e38216696","isNewRecord":false,"remarks":"","createDate":"2018-01-26 06:00:30","updateDate":"2018-01-26 14:00:30","type":null,"deviceSerial":"157403964","pondId":{"id":"315bf13fe39d46ff8423b68a7d5a2b72","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"firmId":null,"name":"1#塘"},"firmId":{"id":"42252b3f6f4f4801a232921ebf852e1d","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"govAquFarmId":null,"firmName":"商丘和昌饲料有限公司","area":null,"coordinateX":null,"coordinateY":null,"picture":null,"mainProduct":null,"comSynopsis":null},"channelNo":"5","ezopen":"ezopen://open.ys7.com/157403964/5.live","ezopenhd":"ezopen://open.ys7.com/157403964/5.hd.live","img":"|/aims/userfiles/1/files/comopinion/videoDataInformation/2018/01/HCDB1.png","videoType":null},{"id":"e33b27f1a0654330befe84de6ce7ba0f","isNewRecord":false,"remarks":"","createDate":"2018-01-26 06:01:17","updateDate":"2018-01-26 14:01:17","type":null,"deviceSerial":"157403964","pondId":{"id":"4e42aaee0b284da793a6e9c2f13ce4d9","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"firmId":null,"name":"2#塘"},"firmId":{"id":"42252b3f6f4f4801a232921ebf852e1d","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"govAquFarmId":null,"firmName":"商丘和昌饲料有限公司","area":null,"coordinateX":null,"coordinateY":null,"picture":null,"mainProduct":null,"comSynopsis":null},"channelNo":"6","ezopen":"ezopen://open.ys7.com/157403964/6.live","ezopenhd":"ezopen://open.ys7.com/157403964/6.hd.live","img":"|/aims/userfiles/1/files/comopinion/videoDataInformation/2018/01/HCDB2.png","videoType":null},{"id":"e98955dc808f4b2dba3e68ed08e7431c","isNewRecord":false,"remarks":"","createDate":"2018-01-26 05:52:22","updateDate":"2018-01-26 13:52:22","type":null,"deviceSerial":"107846746","pondId":{"id":"490b6157970e444db66c2bce0ec64e53","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"firmId":null,"name":"1#塘"},"firmId":{"id":"d562b1bb037d4dc69f5f5994111ca37d","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"govAquFarmId":null,"firmName":"固始金海养殖场","area":null,"coordinateX":null,"coordinateY":null,"picture":null,"mainProduct":null,"comSynopsis":null},"channelNo":"9","ezopen":"ezopen://open.ys7.com/107846746/9.live","ezopenhd":"ezopen://open.ys7.com/107846746/9.hd.live","img":"|/aims/userfiles/1/files/comopinion/videoDataInformation/2018/01/GSJH1.png","videoType":null},{"id":"1a27215ea83945bba87a513e492d2490","isNewRecord":false,"remarks":"","createDate":"2018-01-15 08:42:00","updateDate":"2018-01-26 10:08:50","type":null,"deviceSerial":"835510343","pondId":{"id":"9bb2bbef809943e5a25a12a2a82d4efb","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"firmId":null,"name":"2#塘"},"firmId":{"id":"931d4eb8b2264636a16ed72366da2fb7","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"govAquFarmId":null,"firmName":"新乡原阳豫黄合作社","area":null,"coordinateX":null,"coordinateY":null,"picture":null,"mainProduct":null,"comSynopsis":null},"channelNo":"2","ezopen":"ezopen://open.ys7.com/835510343/2.live","ezopenhd":"ezopen://open.ys7.com/835510343/2.hd.live","img":"|/aims/userfiles/1/files/comopinion/videoDataInformation/2018/01/GSJH1.png","videoType":null},{"id":"c67369e4142143af96fe7fa35372ad10","isNewRecord":false,"remarks":"","createDate":"2018-01-26 06:07:34","updateDate":"2018-01-26 16:17:33","type":null,"deviceSerial":"806232611","pondId":{"id":"5b4fd2721ade4dcf9950f0c1e25d6db6","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"firmId":null,"name":"1#塘"},"firmId":{"id":"64f9e13d89704ff88440b4213783c662","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"govAquFarmId":null,"firmName":"栾川辉煌大鲵养殖场","area":null,"coordinateX":null,"coordinateY":null,"picture":null,"mainProduct":null,"comSynopsis":null},"channelNo":"1","ezopen":"ezopen://AES:4Vc1eH7sxzXDZHvCJNPLMg@open.ys7.com/806232611/1.live","ezopenhd":"ezopen://AES:4Vc1eH7sxzXDZHvCJNPLMg@open.ys7.com/806232611/1.hd.live","img":"|/aims/userfiles/1/files/comopinion/videoDataInformation/2018/01/LC5.png","videoType":null},{"id":"513d3076537f421a87454cf249c0de46","isNewRecord":false,"remarks":"","createDate":"2018-01-26 06:08:58","updateDate":"2018-01-26 16:17:46","type":null,"deviceSerial":"806232611","pondId":{"id":"90bcfef20d82491e933893703d18ae8f","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"firmId":null,"name":"2#塘"},"firmId":{"id":"64f9e13d89704ff88440b4213783c662","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"govAquFarmId":null,"firmName":"栾川辉煌大鲵养殖场","area":null,"coordinateX":null,"coordinateY":null,"picture":null,"mainProduct":null,"comSynopsis":null},"channelNo":"2","ezopen":"ezopen://AES:4Vc1eH7sxzXDZHvCJNPLMg@open.ys7.com/806232611/2.live","ezopenhd":"ezopen://AES:4Vc1eH7sxzXDZHvCJNPLMg@open.ys7.com/806232611/2.hd.live","img":"|/aims/userfiles/1/files/comopinion/videoDataInformation/2018/01/LC7.png","videoType":null},{"id":"d204527ade4b4bf1a3a4d98009a66f10","isNewRecord":false,"remarks":"","createDate":"2017-12-13 02:19:57","updateDate":"2018-01-26 16:18:42","type":null,"deviceSerial":"835510343","pondId":{"id":"7b6cccdcd2454f798084283fb473b9dc","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"firmId":null,"name":"1#塘"},"firmId":{"id":"0c1942301e564813817c9c0b6301fb89","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"govAquFarmId":null,"firmName":"灵宝江辉养殖合作社","area":null,"coordinateX":null,"coordinateY":null,"picture":null,"mainProduct":null,"comSynopsis":null},"channelNo":"1","ezopen":"ezopen://open.ys7.com/835510343/1.live","ezopenhd":"ezopen://open.ys7.com/835510343/1.hd.live","img":"|/aims/userfiles/1/files/comopinion/videoDataInformation/2018/01/LBXB1.png","videoType":null},{"id":"a899712376d04ef78d0bd65ccd0a892f","isNewRecord":false,"remarks":"","createDate":"2017-12-07 09:15:49","updateDate":"2018-01-26 14:16:36","type":null,"deviceSerial":"811832246","pondId":{"id":"8f8ba27633044ed78ec9d65124849e08","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"firmId":null,"name":"1#塘"},"firmId":{"id":"85a2a2a190ee42a9a53d0ab628c817c7","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"govAquFarmId":null,"firmName":"荥阳水产良种场","area":null,"coordinateX":null,"coordinateY":null,"picture":null,"mainProduct":null,"comSynopsis":null},"channelNo":"1","ezopen":"ezopen://open.ys7.com/811832246/1.live","ezopenhd":"ezopen://open.ys7.com/811832246/1.hd.live","img":"","videoType":null}],"maxResults":10,"firstResult":0,"html":"<div class=\"fixed-table-pagination\" style=\"display: block;\"><div class=\"pull-left pagination-detail\"><span class=\"pagination-info\">显示第 1 到第 10 条记录，总共 11 条记录<\/span><span class=\"page-list\">每页显示 <span class=\"btn-group dropup\"><button type=\"button\" class=\"btn btn-default  btn-outline dropdown-toggle\" data-toggle=\"dropdown\" aria-expanded=\"false\"><span class=\"page-size\">10<\/span> <span class=\"caret\"><\/span><\/button><ul class=\"dropdown-menu\" role=\"menu\"><li class=\"active\"><a href=\"javascript:page(1,10,'');\">10<\/a><\/li><li class=\"\"><a href=\"javascript:page(1,25,'');\">25<\/a><\/li><li class=\"\"><a href=\"javascript:page(1,50,'');\">50<\/a><\/li><li class=\"\"><a href=\"javascript:page(1,100,'');\">100<\/a><\/li><\/ul><\/span> 条记录<\/span><\/div><div class=\"pull-right pagination-roll\"><ul class=\"pagination pagination-outline\"><li class=\"paginate_button previous disabled\"><a href=\"javascript:\"><i class=\"fa fa-angle-double-left\"><\/i><\/a><\/li>\n<li class=\"paginate_button previous disabled\"><a href=\"javascript:\"><i class=\"fa fa-angle-left\"><\/i><\/a><\/li>\n<li class=\"paginate_button active\"><a href=\"javascript:\">1<\/a><\/li>\n<li class=\"paginate_button \"><a href=\"javascript:\" onclick=\"page(2,10,'');\">2<\/a><\/li>\n<li class=\"paginate_button next\"><a href=\"javascript:\" onclick=\"page(2,10,'');\"><i class=\"fa fa-angle-right\"><\/i><\/a><\/li>\n<li class=\"paginate_button next\"><a href=\"javascript:\" onclick=\"page(2,10,'');\"><i class=\"fa fa-angle-double-right\"><\/i><\/a><\/li>\n<\/ul><\/div><\/div>"}
     * appKey : 566fb0a1d274443f8d32d74212c570e7
     * accessToken : at.2s6n6xh21uneq5pe14wzrm7h3vwtff77-32f9kr8ig0-0htczey-1ilfzik48
     */

    private VideoListBean videoList;
    private String appKey;
    private String accessToken;

    public VideoListBean getVideoList() {
        return videoList;
    }

    public void setVideoList(VideoListBean videoList) {
        this.videoList = videoList;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public static class VideoListBean {
        /**
         * pageNo : 1
         * pageSize : 10
         * count : 11
         * lastPage : false
         * list : [{"id":"5ef5aa40a59e40bb88efe4597c00ca68","isNewRecord":false,"remarks":"","createDate":"2018-01-26 06:03:37","updateDate":"2018-01-26 14:03:37","type":null,"deviceSerial":"827899404","pondId":{"id":"245f364a06d148d09d7c22f643d79fb5","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"firmId":null,"name":"1#塘"},"firmId":{"id":"633b3a06e2e445ef9f5902e2abb23714","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"govAquFarmId":null,"firmName":"南阳方城良种场","area":null,"coordinateX":null,"coordinateY":null,"picture":null,"mainProduct":null,"comSynopsis":null},"channelNo":"5","ezopen":"ezopen://open.ys7.com/827899404/5.live","ezopenhd":"ezopen://open.ys7.com/827899404/5.hd.live","img":"|/aims/userfiles/1/files/comopinion/videoDataInformation/2018/01/WHTA3.png","videoType":null},{"id":"07c5871772e84522874c8675bbb81937","isNewRecord":false,"remarks":"","createDate":"2018-01-26 06:04:19","updateDate":"2018-01-26 14:16:15","type":null,"deviceSerial":"827899404","pondId":{"id":"af9fb38f1ae3448c9200dc7214b920a7","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"firmId":null,"name":"2#塘"},"firmId":{"id":"633b3a06e2e445ef9f5902e2abb23714","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"govAquFarmId":null,"firmName":"南阳方城良种场","area":null,"coordinateX":null,"coordinateY":null,"picture":null,"mainProduct":null,"comSynopsis":null},"channelNo":"4","ezopen":"ezopen://open.ys7.com/827899404/4.live","ezopenhd":"ezopen://open.ys7.com/827899404/4.hd.live","img":"|/aims/userfiles/1/files/comopinion/videoDataInformation/2018/01/WHTA5.png","videoType":null},{"id":"0b295510ca744e628037596e38216696","isNewRecord":false,"remarks":"","createDate":"2018-01-26 06:00:30","updateDate":"2018-01-26 14:00:30","type":null,"deviceSerial":"157403964","pondId":{"id":"315bf13fe39d46ff8423b68a7d5a2b72","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"firmId":null,"name":"1#塘"},"firmId":{"id":"42252b3f6f4f4801a232921ebf852e1d","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"govAquFarmId":null,"firmName":"商丘和昌饲料有限公司","area":null,"coordinateX":null,"coordinateY":null,"picture":null,"mainProduct":null,"comSynopsis":null},"channelNo":"5","ezopen":"ezopen://open.ys7.com/157403964/5.live","ezopenhd":"ezopen://open.ys7.com/157403964/5.hd.live","img":"|/aims/userfiles/1/files/comopinion/videoDataInformation/2018/01/HCDB1.png","videoType":null},{"id":"e33b27f1a0654330befe84de6ce7ba0f","isNewRecord":false,"remarks":"","createDate":"2018-01-26 06:01:17","updateDate":"2018-01-26 14:01:17","type":null,"deviceSerial":"157403964","pondId":{"id":"4e42aaee0b284da793a6e9c2f13ce4d9","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"firmId":null,"name":"2#塘"},"firmId":{"id":"42252b3f6f4f4801a232921ebf852e1d","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"govAquFarmId":null,"firmName":"商丘和昌饲料有限公司","area":null,"coordinateX":null,"coordinateY":null,"picture":null,"mainProduct":null,"comSynopsis":null},"channelNo":"6","ezopen":"ezopen://open.ys7.com/157403964/6.live","ezopenhd":"ezopen://open.ys7.com/157403964/6.hd.live","img":"|/aims/userfiles/1/files/comopinion/videoDataInformation/2018/01/HCDB2.png","videoType":null},{"id":"e98955dc808f4b2dba3e68ed08e7431c","isNewRecord":false,"remarks":"","createDate":"2018-01-26 05:52:22","updateDate":"2018-01-26 13:52:22","type":null,"deviceSerial":"107846746","pondId":{"id":"490b6157970e444db66c2bce0ec64e53","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"firmId":null,"name":"1#塘"},"firmId":{"id":"d562b1bb037d4dc69f5f5994111ca37d","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"govAquFarmId":null,"firmName":"固始金海养殖场","area":null,"coordinateX":null,"coordinateY":null,"picture":null,"mainProduct":null,"comSynopsis":null},"channelNo":"9","ezopen":"ezopen://open.ys7.com/107846746/9.live","ezopenhd":"ezopen://open.ys7.com/107846746/9.hd.live","img":"|/aims/userfiles/1/files/comopinion/videoDataInformation/2018/01/GSJH1.png","videoType":null},{"id":"1a27215ea83945bba87a513e492d2490","isNewRecord":false,"remarks":"","createDate":"2018-01-15 08:42:00","updateDate":"2018-01-26 10:08:50","type":null,"deviceSerial":"835510343","pondId":{"id":"9bb2bbef809943e5a25a12a2a82d4efb","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"firmId":null,"name":"2#塘"},"firmId":{"id":"931d4eb8b2264636a16ed72366da2fb7","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"govAquFarmId":null,"firmName":"新乡原阳豫黄合作社","area":null,"coordinateX":null,"coordinateY":null,"picture":null,"mainProduct":null,"comSynopsis":null},"channelNo":"2","ezopen":"ezopen://open.ys7.com/835510343/2.live","ezopenhd":"ezopen://open.ys7.com/835510343/2.hd.live","img":"|/aims/userfiles/1/files/comopinion/videoDataInformation/2018/01/GSJH1.png","videoType":null},{"id":"c67369e4142143af96fe7fa35372ad10","isNewRecord":false,"remarks":"","createDate":"2018-01-26 06:07:34","updateDate":"2018-01-26 16:17:33","type":null,"deviceSerial":"806232611","pondId":{"id":"5b4fd2721ade4dcf9950f0c1e25d6db6","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"firmId":null,"name":"1#塘"},"firmId":{"id":"64f9e13d89704ff88440b4213783c662","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"govAquFarmId":null,"firmName":"栾川辉煌大鲵养殖场","area":null,"coordinateX":null,"coordinateY":null,"picture":null,"mainProduct":null,"comSynopsis":null},"channelNo":"1","ezopen":"ezopen://AES:4Vc1eH7sxzXDZHvCJNPLMg@open.ys7.com/806232611/1.live","ezopenhd":"ezopen://AES:4Vc1eH7sxzXDZHvCJNPLMg@open.ys7.com/806232611/1.hd.live","img":"|/aims/userfiles/1/files/comopinion/videoDataInformation/2018/01/LC5.png","videoType":null},{"id":"513d3076537f421a87454cf249c0de46","isNewRecord":false,"remarks":"","createDate":"2018-01-26 06:08:58","updateDate":"2018-01-26 16:17:46","type":null,"deviceSerial":"806232611","pondId":{"id":"90bcfef20d82491e933893703d18ae8f","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"firmId":null,"name":"2#塘"},"firmId":{"id":"64f9e13d89704ff88440b4213783c662","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"govAquFarmId":null,"firmName":"栾川辉煌大鲵养殖场","area":null,"coordinateX":null,"coordinateY":null,"picture":null,"mainProduct":null,"comSynopsis":null},"channelNo":"2","ezopen":"ezopen://AES:4Vc1eH7sxzXDZHvCJNPLMg@open.ys7.com/806232611/2.live","ezopenhd":"ezopen://AES:4Vc1eH7sxzXDZHvCJNPLMg@open.ys7.com/806232611/2.hd.live","img":"|/aims/userfiles/1/files/comopinion/videoDataInformation/2018/01/LC7.png","videoType":null},{"id":"d204527ade4b4bf1a3a4d98009a66f10","isNewRecord":false,"remarks":"","createDate":"2017-12-13 02:19:57","updateDate":"2018-01-26 16:18:42","type":null,"deviceSerial":"835510343","pondId":{"id":"7b6cccdcd2454f798084283fb473b9dc","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"firmId":null,"name":"1#塘"},"firmId":{"id":"0c1942301e564813817c9c0b6301fb89","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"govAquFarmId":null,"firmName":"灵宝江辉养殖合作社","area":null,"coordinateX":null,"coordinateY":null,"picture":null,"mainProduct":null,"comSynopsis":null},"channelNo":"1","ezopen":"ezopen://open.ys7.com/835510343/1.live","ezopenhd":"ezopen://open.ys7.com/835510343/1.hd.live","img":"|/aims/userfiles/1/files/comopinion/videoDataInformation/2018/01/LBXB1.png","videoType":null},{"id":"a899712376d04ef78d0bd65ccd0a892f","isNewRecord":false,"remarks":"","createDate":"2017-12-07 09:15:49","updateDate":"2018-01-26 14:16:36","type":null,"deviceSerial":"811832246","pondId":{"id":"8f8ba27633044ed78ec9d65124849e08","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"firmId":null,"name":"1#塘"},"firmId":{"id":"85a2a2a190ee42a9a53d0ab628c817c7","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"govAquFarmId":null,"firmName":"荥阳水产良种场","area":null,"coordinateX":null,"coordinateY":null,"picture":null,"mainProduct":null,"comSynopsis":null},"channelNo":"1","ezopen":"ezopen://open.ys7.com/811832246/1.live","ezopenhd":"ezopen://open.ys7.com/811832246/1.hd.live","img":"","videoType":null}]
         * maxResults : 10
         * firstResult : 0
         * html : <div class="fixed-table-pagination" style="display: block;"><div class="pull-left pagination-detail"><span class="pagination-info">显示第 1 到第 10 条记录，总共 11 条记录</span><span class="page-list">每页显示 <span class="btn-group dropup"><button type="button" class="btn btn-default  btn-outline dropdown-toggle" data-toggle="dropdown" aria-expanded="false"><span class="page-size">10</span> <span class="caret"></span></button><ul class="dropdown-menu" role="menu"><li class="active"><a href="javascript:page(1,10,'');">10</a></li><li class=""><a href="javascript:page(1,25,'');">25</a></li><li class=""><a href="javascript:page(1,50,'');">50</a></li><li class=""><a href="javascript:page(1,100,'');">100</a></li></ul></span> 条记录</span></div><div class="pull-right pagination-roll"><ul class="pagination pagination-outline"><li class="paginate_button previous disabled"><a href="javascript:"><i class="fa fa-angle-double-left"></i></a></li>
         <li class="paginate_button previous disabled"><a href="javascript:"><i class="fa fa-angle-left"></i></a></li>
         <li class="paginate_button active"><a href="javascript:">1</a></li>
         <li class="paginate_button "><a href="javascript:" onclick="page(2,10,'');">2</a></li>
         <li class="paginate_button next"><a href="javascript:" onclick="page(2,10,'');"><i class="fa fa-angle-right"></i></a></li>
         <li class="paginate_button next"><a href="javascript:" onclick="page(2,10,'');"><i class="fa fa-angle-double-right"></i></a></li>
         </ul></div></div>
         */

        private int pageNo;
        private int pageSize;
        private int count;
        private boolean lastPage;
        private int maxResults;
        private int firstResult;
        private String html;
        private List<ListBean> list;

        public int getPageNo() {
            return pageNo;
        }

        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public boolean isLastPage() {
            return lastPage;
        }

        public void setLastPage(boolean lastPage) {
            this.lastPage = lastPage;
        }

        public int getMaxResults() {
            return maxResults;
        }

        public void setMaxResults(int maxResults) {
            this.maxResults = maxResults;
        }

        public int getFirstResult() {
            return firstResult;
        }

        public void setFirstResult(int firstResult) {
            this.firstResult = firstResult;
        }

        public String getHtml() {
            return html;
        }

        public void setHtml(String html) {
            this.html = html;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean implements Serializable {
            /**
             * id : 5ef5aa40a59e40bb88efe4597c00ca68
             * isNewRecord : false
             * remarks :
             * createDate : 2018-01-26 06:03:37
             * updateDate : 2018-01-26 14:03:37
             * type : null
             * deviceSerial : 827899404
             * pondId : {"id":"245f364a06d148d09d7c22f643d79fb5","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"firmId":null,"name":"1#塘"}
             * firmId : {"id":"633b3a06e2e445ef9f5902e2abb23714","isNewRecord":false,"remarks":null,"createDate":null,"updateDate":null,"govAquFarmId":null,"firmName":"南阳方城良种场","area":null,"coordinateX":null,"coordinateY":null,"picture":null,"mainProduct":null,"comSynopsis":null}
             * channelNo : 5
             * ezopen : ezopen://open.ys7.com/827899404/5.live
             * ezopenhd : ezopen://open.ys7.com/827899404/5.hd.live
             * img : |/aims/userfiles/1/files/comopinion/videoDataInformation/2018/01/WHTA3.png
             * videoType : null
             */

            private String id;
            private boolean isNewRecord;
            private String remarks;
            private String createDate;
            private String updateDate;
            private Object type;
            private String deviceSerial;
            private PondIdBean pondId;
            private FirmIdBean firmId;
            private String channelNo;
            private String ezopen;
            private String ezopenhd;
            private String img;
            private Object videoType;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public boolean isIsNewRecord() {
                return isNewRecord;
            }

            public void setIsNewRecord(boolean isNewRecord) {
                this.isNewRecord = isNewRecord;
            }

            public String getRemarks() {
                return remarks;
            }

            public void setRemarks(String remarks) {
                this.remarks = remarks;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public String getUpdateDate() {
                return updateDate;
            }

            public void setUpdateDate(String updateDate) {
                this.updateDate = updateDate;
            }

            public Object getType() {
                return type;
            }

            public void setType(Object type) {
                this.type = type;
            }

            public String getDeviceSerial() {
                return deviceSerial;
            }

            public void setDeviceSerial(String deviceSerial) {
                this.deviceSerial = deviceSerial;
            }

            public PondIdBean getPondId() {
                return pondId;
            }

            public void setPondId(PondIdBean pondId) {
                this.pondId = pondId;
            }

            public FirmIdBean getFirmId() {
                return firmId;
            }

            public void setFirmId(FirmIdBean firmId) {
                this.firmId = firmId;
            }

            public String getChannelNo() {
                return channelNo;
            }

            public void setChannelNo(String channelNo) {
                this.channelNo = channelNo;
            }

            public String getEzopen() {
                return ezopen;
            }

            public void setEzopen(String ezopen) {
                this.ezopen = ezopen;
            }

            public String getEzopenhd() {
                return ezopenhd;
            }

            public void setEzopenhd(String ezopenhd) {
                this.ezopenhd = ezopenhd;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public Object getVideoType() {
                return videoType;
            }

            public void setVideoType(Object videoType) {
                this.videoType = videoType;
            }

            public static class PondIdBean {
                /**
                 * id : 245f364a06d148d09d7c22f643d79fb5
                 * isNewRecord : false
                 * remarks : null
                 * createDate : null
                 * updateDate : null
                 * firmId : null
                 * name : 1#塘
                 */

                private String id;
                private boolean isNewRecord;
                private Object remarks;
                private Object createDate;
                private Object updateDate;
                private Object firmId;
                private String name;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public boolean isIsNewRecord() {
                    return isNewRecord;
                }

                public void setIsNewRecord(boolean isNewRecord) {
                    this.isNewRecord = isNewRecord;
                }

                public Object getRemarks() {
                    return remarks;
                }

                public void setRemarks(Object remarks) {
                    this.remarks = remarks;
                }

                public Object getCreateDate() {
                    return createDate;
                }

                public void setCreateDate(Object createDate) {
                    this.createDate = createDate;
                }

                public Object getUpdateDate() {
                    return updateDate;
                }

                public void setUpdateDate(Object updateDate) {
                    this.updateDate = updateDate;
                }

                public Object getFirmId() {
                    return firmId;
                }

                public void setFirmId(Object firmId) {
                    this.firmId = firmId;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }

            public static class FirmIdBean {
                /**
                 * id : 633b3a06e2e445ef9f5902e2abb23714
                 * isNewRecord : false
                 * remarks : null
                 * createDate : null
                 * updateDate : null
                 * govAquFarmId : null
                 * firmName : 南阳方城良种场
                 * area : null
                 * coordinateX : null
                 * coordinateY : null
                 * picture : null
                 * mainProduct : null
                 * comSynopsis : null
                 */

                private String id;
                private boolean isNewRecord;
                private Object remarks;
                private Object createDate;
                private Object updateDate;
                private Object govAquFarmId;
                private String firmName;
                private Object area;
                private Object coordinateX;
                private Object coordinateY;
                private Object picture;
                private Object mainProduct;
                private Object comSynopsis;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public boolean isIsNewRecord() {
                    return isNewRecord;
                }

                public void setIsNewRecord(boolean isNewRecord) {
                    this.isNewRecord = isNewRecord;
                }

                public Object getRemarks() {
                    return remarks;
                }

                public void setRemarks(Object remarks) {
                    this.remarks = remarks;
                }

                public Object getCreateDate() {
                    return createDate;
                }

                public void setCreateDate(Object createDate) {
                    this.createDate = createDate;
                }

                public Object getUpdateDate() {
                    return updateDate;
                }

                public void setUpdateDate(Object updateDate) {
                    this.updateDate = updateDate;
                }

                public Object getGovAquFarmId() {
                    return govAquFarmId;
                }

                public void setGovAquFarmId(Object govAquFarmId) {
                    this.govAquFarmId = govAquFarmId;
                }

                public String getFirmName() {
                    return firmName;
                }

                public void setFirmName(String firmName) {
                    this.firmName = firmName;
                }

                public Object getArea() {
                    return area;
                }

                public void setArea(Object area) {
                    this.area = area;
                }

                public Object getCoordinateX() {
                    return coordinateX;
                }

                public void setCoordinateX(Object coordinateX) {
                    this.coordinateX = coordinateX;
                }

                public Object getCoordinateY() {
                    return coordinateY;
                }

                public void setCoordinateY(Object coordinateY) {
                    this.coordinateY = coordinateY;
                }

                public Object getPicture() {
                    return picture;
                }

                public void setPicture(Object picture) {
                    this.picture = picture;
                }

                public Object getMainProduct() {
                    return mainProduct;
                }

                public void setMainProduct(Object mainProduct) {
                    this.mainProduct = mainProduct;
                }

                public Object getComSynopsis() {
                    return comSynopsis;
                }

                public void setComSynopsis(Object comSynopsis) {
                    this.comSynopsis = comSynopsis;
                }
            }
        }
    }
}
