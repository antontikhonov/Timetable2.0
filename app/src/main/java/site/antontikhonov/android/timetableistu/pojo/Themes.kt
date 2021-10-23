package site.antontikhonov.android.timetableistu.pojo

data class Themes(
    val themes: List<ThemeEntity>
)

data class ThemeEntity(
    val id: String,
    val name: String,
    val url: String
)