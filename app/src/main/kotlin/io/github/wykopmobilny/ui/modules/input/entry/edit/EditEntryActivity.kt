package io.github.wykopmobilny.ui.modules.input.entry.edit

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import io.github.wykopmobilny.R
import io.github.wykopmobilny.api.suggest.SuggestApi
import io.github.wykopmobilny.ui.modules.input.BaseInputActivity
import javax.inject.Inject

class EditEntryActivity : BaseInputActivity<EditEntryPresenter>(), EditEntryView {

    companion object {
        const val EXTRA_ENTRY_ID = "ENTRY_ID"

        fun createIntent(context: Context, body: String, entryId: Long) =
            Intent(context, EditEntryActivity::class.java).apply {
                putExtra(EXTRA_BODY, body)
                putExtra(EXTRA_ENTRY_ID, entryId)
            }
    }

    @Inject
    override lateinit var presenter: EditEntryPresenter

    @Inject
    override lateinit var suggestionApi: SuggestApi

    override val entryId by lazy { intent.getLongExtra(EXTRA_ENTRY_ID, 0) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.subscribe(this)
        setupSuggestions()
        supportActionBar?.setTitle(R.string.edit_entry)
    }

    override fun onDestroy() {
        presenter.unsubscribe()
        super.onDestroy()
    }

    override fun exitActivity() {
        val data = Intent()
        data.putExtra("entryId", entryId)
        data.putExtra("entryBody", textBody)
        setResult(Activity.RESULT_OK, data)
        finish()
    }
}
