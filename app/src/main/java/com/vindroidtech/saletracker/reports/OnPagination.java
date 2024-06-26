package com.vindroidtech.saletracker.reports;

import android.view.View;

public interface OnPagination {
     void onFirstClicked(View view);
     void onPreClicked(View view);
     void onNextClicked(View view);
     void onLastClicked(View view);
}
