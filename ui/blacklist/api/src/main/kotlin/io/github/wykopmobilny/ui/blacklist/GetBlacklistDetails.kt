package io.github.wykopmobilny.ui.blacklist

import io.github.wykopmobilny.ui.base.Query
import io.github.wykopmobilny.ui.base.components.ErrorDialogUi

interface GetBlacklistDetails : Query<BlacklistedDetailsUi>

class BlacklistedDetailsUi(
    val snackbar: String? = null, // TODO @mk : 19/07/2021 not supported yet
    val errorDialog: ErrorDialogUi?,
    val content: Content,
) {

    sealed class Content {

        data class Empty(
            val isLoading: Boolean,
            val loadAction: () -> Unit,
        ) : Content()

        data class WithData(
            val tags: ElementPage,
            val users: ElementPage,
        ) : Content() {

            data class ElementPage(
                val isRefreshing: Boolean,
                val refreshAction: () -> Unit,
                val elements: List<BlacklistedElementUi>,
            )
        }
    }
}

class BlacklistedElementUi(
    val name: String,
    val state: StateUi,
) {

    sealed class StateUi {

        data class Default(val unblock: () -> Unit) : StateUi()

        object InProgress : StateUi()

        data class Error(val showError: () -> Unit) : StateUi()
    }
}
