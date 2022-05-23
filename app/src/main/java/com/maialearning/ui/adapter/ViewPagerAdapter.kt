import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.maialearning.ui.fragments.MessageListFragment

@Suppress("DEPRECATION")
class ViewPagerAdapter(
	var context: Context,
	fm: Fragment,
	var totalTabs: Int
) : FragmentStateAdapter(fm) {
	override fun getItemCount(): Int = totalTabs

	override fun createFragment(position: Int): Fragment {
		// Return a NEW fragment instance in createFragment(int)
		val fragment =MessageListFragment()

		return fragment
	}

}