package io.github.wykopmobilny.ui.settings

import io.github.wykopmobilny.ui.base.Query

interface GetAppearancePreferences : Query<AppearancePreferencesUi>

data class AppearancePreferencesUi(
    val appearance: AppearanceSectionUi,
    val mediaPlayerSection: MediaPlayerSectionUi,
    val mikroblogSection: MikroblogSectionUi,
    val linksSection: LinksSectionUi,
    val imagesSection: ImagesSectionUi,
) {

    data class AppearanceSectionUi(
        val userThemeSetting: ListSetting<AppThemeUi>,
        val useAmoledTheme: Setting,
        val startScreen: ListSetting<MainScreenUi>,
        val fontSize: ListSetting<FontSizeUi>,
        val disableEdgeSlide: Setting,
    )

    data class MediaPlayerSectionUi(
        val enableYoutubePlayer: Setting,
        val enableEmbedPlayer: Setting,
    )

    data class MikroblogSectionUi(
        val mikroblogScreen: ListSetting<MikroblogScreenUi>,
        val cutLongEntries: Setting,
        val openSpoilersInDialog: Setting,
    )

    data class LinksSectionUi(
        val useSimpleList: Setting,
        val showLinkThumbnail: Setting,
        val imagePosition: ListSetting<LinkImagePositionUi>,
        val showAuthor: Setting,
    )

    data class ImagesSectionUi(
        val showMinifiedImages: Setting,
        val cutImages: Setting,
        val cutImagesProportion: SliderSetting,
    )
}

enum class AppThemeUi {
    Automatic,
    Light,
    Dark
}

enum class MainScreenUi {
    Promoted,
    Mikroblog,
    MyWykop,
    Hits,
}

enum class FontSizeUi {
    VerySmall,
    Small,
    Normal,
    Large,
    VeryLarge,
}

enum class MikroblogScreenUi {
    Active,
    Newest,
    SixHours,
    TwelveHours,
    TwentyFourHours,
}

enum class LinkImagePositionUi {
    Left,
    Right,
    Top,
    Bottom
}
