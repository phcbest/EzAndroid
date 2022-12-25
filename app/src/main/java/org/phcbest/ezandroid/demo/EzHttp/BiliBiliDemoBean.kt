package org.phcbest.ezandroid.demo.EzHttp

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class BiliBiliDemoBean(
    @Json(name = "code")
    var code: Int, // 0
    @Json(name = "data")
    var `data`: Data,
    @Json(name = "message")
    var message: String, // 0
    @Json(name = "ttl")
    var ttl: Int // 1
) {
    @JsonClass(generateAdapter = true)
    data class Data(
        @Json(name = "birthday")
        var birthday: String,
        @Json(name = "coins")
        var coins: Int, // 0
        @Json(name = "contract")
        var contract: Contract,
        @Json(name = "elec")
        var elec: Elec,
        @Json(name = "face")
        var face: String, // https://i2.hdslb.com/bfs/face/22c40d9f5569da64fc3a2a2c8e219fed38722c6d.jpg
        @Json(name = "face_nft")
        var faceNft: Int, // 0
        @Json(name = "face_nft_type")
        var faceNftType: Int, // 0
        @Json(name = "fans_badge")
        var fansBadge: Boolean, // true
        @Json(name = "fans_medal")
        var fansMedal: FansMedal,
        @Json(name = "gaia_data")
        var gaiaData: Any, // null
        @Json(name = "gaia_res_type")
        var gaiaResType: Int, // 0
        @Json(name = "is_followed")
        var isFollowed: Boolean, // true
        @Json(name = "is_risk")
        var isRisk: Boolean, // false
        @Json(name = "is_senior_member")
        var isSeniorMember: Int, // 0
        @Json(name = "jointime")
        var jointime: Int, // 0
        @Json(name = "level")
        var level: Int, // 6
        @Json(name = "live_room")
        var liveRoom: LiveRoom,
        @Json(name = "mcn_info")
        var mcnInfo: Any, // null
        @Json(name = "mid")
        var mid: Int, // 5408366
        @Json(name = "moral")
        var moral: Int, // 0
        @Json(name = "name")
        var name: String, // 库库_sama
        @Json(name = "nameplate")
        var nameplate: Nameplate,
        @Json(name = "official")
        var official: Official,
        @Json(name = "pendant")
        var pendant: Pendant,
        @Json(name = "profession")
        var profession: Profession,
        @Json(name = "rank")
        var rank: Int, // 10000
        @Json(name = "school")
        var school: Any, // null
        @Json(name = "series")
        var series: Series,
        @Json(name = "sex")
        var sex: String, // 保密
        @Json(name = "sign")
        var sign: String, // 我想
        @Json(name = "silence")
        var silence: Int, // 0
        @Json(name = "sys_notice")
        var sysNotice: SysNotice,
        @Json(name = "tags")
        var tags: Any, // null
        @Json(name = "theme")
        var theme: Theme,
        @Json(name = "top_photo")
        var topPhoto: String, // http://i1.hdslb.com/bfs/space/cb1c3ef50e22b6096fde67febe863494caefebad.png
        @Json(name = "user_honour_info")
        var userHonourInfo: UserHonourInfo,
        @Json(name = "vip")
        var vip: Vip
    ) {
        @JsonClass(generateAdapter = true)
        data class Contract(
            @Json(name = "is_display")
            var isDisplay: Boolean, // false
            @Json(name = "is_follow_display")
            var isFollowDisplay: Boolean // false
        )

        @JsonClass(generateAdapter = true)
        data class Elec(
            @Json(name = "show_info")
            var showInfo: ShowInfo
        ) {
            @JsonClass(generateAdapter = true)
            data class ShowInfo(
                @Json(name = "icon")
                var icon: String,
                @Json(name = "jump_url")
                var jumpUrl: String,
                @Json(name = "show")
                var show: Boolean, // true
                @Json(name = "state")
                var state: Int, // 1
                @Json(name = "title")
                var title: String
            )
        }

        @JsonClass(generateAdapter = true)
        data class FansMedal(
            @Json(name = "medal")
            var medal: Any, // null
            @Json(name = "show")
            var show: Boolean, // false
            @Json(name = "wear")
            var wear: Boolean // false
        )

        @JsonClass(generateAdapter = true)
        data class LiveRoom(
            @Json(name = "broadcast_type")
            var broadcastType: Int, // 0
            @Json(name = "cover")
            var cover: String, // http://i0.hdslb.com/bfs/live/new_room_cover/901128619d9b8062e99c21af13e9cdd281a6274e.jpg
            @Json(name = "liveStatus")
            var liveStatus: Int, // 1
            @Json(name = "roomStatus")
            var roomStatus: Int, // 1
            @Json(name = "roomid")
            var roomid: Int, // 5194110
            @Json(name = "roundStatus")
            var roundStatus: Int, // 0
            @Json(name = "title")
            var title: String, // 爆杀！！
            @Json(name = "url")
            var url: String, // https://live.bilibili.com/5194110?broadcast_type=0&is_room_feed=1
            @Json(name = "watched_show")
            var watchedShow: WatchedShow
        ) {
            @JsonClass(generateAdapter = true)
            data class WatchedShow(
                @Json(name = "icon")
                var icon: String, // https://i0.hdslb.com/bfs/live/a725a9e61242ef44d764ac911691a7ce07f36c1d.png
                @Json(name = "icon_location")
                var iconLocation: String,
                @Json(name = "icon_web")
                var iconWeb: String, // https://i0.hdslb.com/bfs/live/8d9d0f33ef8bf6f308742752d13dd0df731df19c.png
                @Json(name = "num")
                var num: Int, // 10383
                @Json(name = "switch")
                var switch: Boolean, // true
                @Json(name = "text_large")
                var textLarge: String, // 1.0万人看过
                @Json(name = "text_small")
                var textSmall: String // 1.0万
            )
        }

        @JsonClass(generateAdapter = true)
        data class Nameplate(
            @Json(name = "condition")
            var condition: String, // 直播主播等级>=30级
            @Json(name = "image")
            var image: String, // https://i1.hdslb.com/bfs/face/a1bf58db4a48f4ec394627d96af7399456812bbb.png
            @Json(name = "image_small")
            var imageSmall: String, // https://i1.hdslb.com/bfs/face/9d1ab43e5064834e0081c46990c7f4d1228ac69c.png
            @Json(name = "level")
            var level: String, // 稀有勋章
            @Json(name = "name")
            var name: String, // 直播车神
            @Json(name = "nid")
            var nid: Int // 39
        )

        @JsonClass(generateAdapter = true)
        data class Official(
            @Json(name = "desc")
            var desc: String,
            @Json(name = "role")
            var role: Int, // 7
            @Json(name = "title")
            var title: String, // bilibili 直播高能主播
            @Json(name = "type")
            var type: Int // 0
        )

        @JsonClass(generateAdapter = true)
        data class Pendant(
            @Json(name = "expire")
            var expire: Int, // 0
            @Json(name = "image")
            var image: String,
            @Json(name = "image_enhance")
            var imageEnhance: String,
            @Json(name = "image_enhance_frame")
            var imageEnhanceFrame: String,
            @Json(name = "name")
            var name: String,
            @Json(name = "pid")
            var pid: Int // 0
        )

        @JsonClass(generateAdapter = true)
        data class Profession(
            @Json(name = "department")
            var department: String,
            @Json(name = "is_show")
            var isShow: Int, // 0
            @Json(name = "name")
            var name: String,
            @Json(name = "title")
            var title: String
        )

        @JsonClass(generateAdapter = true)
        data class Series(
            @Json(name = "show_upgrade_window")
            var showUpgradeWindow: Boolean, // false
            @Json(name = "user_upgrade_status")
            var userUpgradeStatus: Int // 3
        )

        @JsonClass(generateAdapter = true)
        class SysNotice

        @JsonClass(generateAdapter = true)
        class Theme

        @JsonClass(generateAdapter = true)
        data class UserHonourInfo(
            @Json(name = "colour")
            var colour: Any, // null
            @Json(name = "mid")
            var mid: Int, // 0
            @Json(name = "tags")
            var tags: List<Any>
        )

        @JsonClass(generateAdapter = true)
        data class Vip(
            @Json(name = "avatar_subscript")
            var avatarSubscript: Int, // 1
            @Json(name = "avatar_subscript_url")
            var avatarSubscriptUrl: String,
            @Json(name = "due_date")
            var dueDate: Long, // 1725033600000
            @Json(name = "label")
            var label: Label,
            @Json(name = "nickname_color")
            var nicknameColor: String, // #FB7299
            @Json(name = "role")
            var role: Int, // 3
            @Json(name = "status")
            var status: Int, // 1
            @Json(name = "theme_type")
            var themeType: Int, // 0
            @Json(name = "tv_vip_pay_type")
            var tvVipPayType: Int, // 0
            @Json(name = "tv_vip_status")
            var tvVipStatus: Int, // 0
            @Json(name = "type")
            var type: Int, // 2
            @Json(name = "vip_pay_type")
            var vipPayType: Int // 1
        ) {
            @JsonClass(generateAdapter = true)
            data class Label(
                @Json(name = "bg_color")
                var bgColor: String, // #FB7299
                @Json(name = "bg_style")
                var bgStyle: Int, // 1
                @Json(name = "border_color")
                var borderColor: String,
                @Json(name = "img_label_uri_hans")
                var imgLabelUriHans: String,
                @Json(name = "img_label_uri_hans_static")
                var imgLabelUriHansStatic: String, // https://i0.hdslb.com/bfs/vip/8d4f8bfc713826a5412a0a27eaaac4d6b9ede1d9.png
                @Json(name = "img_label_uri_hant")
                var imgLabelUriHant: String,
                @Json(name = "img_label_uri_hant_static")
                var imgLabelUriHantStatic: String, // https://i0.hdslb.com/bfs/activity-plat/static/20220614/e369244d0b14644f5e1a06431e22a4d5/VEW8fCC0hg.png
                @Json(name = "label_theme")
                var labelTheme: String, // annual_vip
                @Json(name = "path")
                var path: String,
                @Json(name = "text")
                var text: String, // 年度大会员
                @Json(name = "text_color")
                var textColor: String, // #FFFFFF
                @Json(name = "use_img_label")
                var useImgLabel: Boolean // true
            )
        }
    }
}