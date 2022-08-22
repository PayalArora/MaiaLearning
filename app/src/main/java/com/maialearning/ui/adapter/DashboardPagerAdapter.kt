//import android.content.Context
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.FragmentManager
//import androidx.fragment.app.FragmentPagerAdapter
//import androidx.viewpager2.adapter.FragmentStateAdapter
//import com.maialearning.model.SortedDateModel
//import com.maialearning.ui.fragments.MessageListFragment
//import com.maialearning.ui.fragments.OverDueFragment
//import com.maialearning.ui.fragments.UpcomingFragment
//import com.maialearning.viewmodel.DashboardFragViewModel
//
//@Suppress("DEPRECATION")
//class DashboardPagerAdapter(
//    var context: Context,
//    fm: Fragment,
//    var totalTabs: Int,
//    val dashboardViewModel: DashboardFragViewModel,
//    var endList: ArrayList<SortedDateModel>,
//    var endCompletedList: ArrayList<SortedDateModel>
//) : FragmentStateAdapter(fm) {
//    override fun getItemCount(): Int = totalTabs
//
//    override fun createFragment(position: Int): Fragment {
//        // Return a NEW fragment instance in createFragment(int)
//        val fragment = UpcomingFragment(dashboardViewModel, endList)
//        if (position == 0) {
//            return fragment
//        } else if (position == 1) {
//            return OverDueFragment(dashboardViewModel, endList)
//        } else if (position == 2) {
//            return OverDueFragment(dashboardViewModel, endCompletedList)
//        }
//        return fragment
//    }
//
//}