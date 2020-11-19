package com.blacklane.shared.app

import androidx.fragment.app.Fragment
import com.blacklane.shared.R


/**
 * Base fragment providing common config
 */
abstract class BaseFragment : Fragment() {
    val baseActivity: BaseActivity?
        get() = activity as? BaseActivity

    companion object {
        fun show(fragment: BaseFragment, activity: BaseActivity?) {
            if (null == activity) return
            activity.supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container_view_tag, fragment)
                .addToBackStack(null)
                .commit()
        }
    }
}