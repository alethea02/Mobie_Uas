package com.azhar.hitungpengeluaran.view;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.azhar.hitungpengeluaran.view.fragment.ChartFragment;
import com.azhar.hitungpengeluaran.view.fragment.pemasukan.PemasukanFragment;
import com.azhar.hitungpengeluaran.view.fragment.pengeluaran.PengeluaranFragment;

/**
 * Created by Azhar Rivaldi on 21-10-2022
 * Youtube Channel : https://bit.ly/2PJMowZ
 * Github : https://github.com/AzharRivaldi
 * Twitter : https://twitter.com/azharrvldi_
 * Instagram : https://www.instagram.com/azhardvls_
 * LinkedIn : https://www.linkedin.com/in/azhar-rivaldi
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

   public ViewPagerAdapter(FragmentManager manager) {
      super(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
   }

   @Override
   public Fragment getItem(int position) {
      Fragment fragment = null;

      switch (position) {
         case 0:
            fragment = new PengeluaranFragment();
            break;
         case 1:
            fragment = new PemasukanFragment();
            break;
         case 2:
            fragment = new ChartFragment();
            break;
      }
      return fragment;
   }

   @Override
   public int getCount() {
      return 3; // Updated to 3 because we added a new fragment
   }

   @Override
   public CharSequence getPageTitle(int position) {
      String strTitle = "";
      switch (position) {
         case 0:
            strTitle = "Pengeluaran";
            break;
         case 1:
            strTitle = "Pemasukan";
            break;
         case 2:
            strTitle = "Chart";
            break;
      }
      return strTitle;
   }
}
