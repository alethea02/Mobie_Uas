package com.azhar.hitungpengeluaran.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.azhar.hitungpengeluaran.R;
import com.azhar.hitungpengeluaran.database.AppDatabase;
import com.azhar.hitungpengeluaran.database.dao.DatabaseDao;
import com.azhar.hitungpengeluaran.model.ModelDatabase;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChartFragment extends Fragment {

    private BarChart barChart;
    private PieChart pieChart;
    private DatabaseDao databaseDao;
    private ExecutorService executorService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_grafik_keuangan, container, false);
        barChart = view.findViewById(R.id.barChart);
        pieChart = view.findViewById(R.id.pieChart);

        AppDatabase db = AppDatabase.getInstance(requireContext());
        databaseDao = db.databaseDao();
        executorService = Executors.newSingleThreadExecutor();

        setupBarChart();
        setupPieChart();

        return view;
    }

    private void setupBarChart() {
        LiveData<Integer> liveDataTotalPemasukan = databaseDao.getTotalPemasukan();
        LiveData<Integer> liveDataTotalPengeluaran = databaseDao.getTotalPengeluaran();

        liveDataTotalPemasukan.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer totalPemasukan) {
                liveDataTotalPengeluaran.observe(getViewLifecycleOwner(), new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer totalPengeluaran) {
                        int totalIncome = totalPemasukan != null ? totalPemasukan : 0;
                        int totalExpense = totalPengeluaran != null ? totalPengeluaran : 0;

                        List<BarEntry> incomeEntries = new ArrayList<>();
                        List<BarEntry> expenseEntries = new ArrayList<>();

                        // Simpan entri untuk pemasukan
                        incomeEntries.add(new BarEntry(0, totalIncome));

                        // Simpan entri untuk pengeluaran
                        expenseEntries.add(new BarEntry(1, totalExpense));

                        BarDataSet incomeDataSet = new BarDataSet(incomeEntries, "Pemasukan");
                        incomeDataSet.setColor(Color.GREEN);
                        incomeDataSet.setValueTextSize(10f);

                        BarDataSet expenseDataSet = new BarDataSet(expenseEntries, "Pengeluaran");
                        expenseDataSet.setColor(Color.RED);
                        expenseDataSet.setValueTextSize(10f);

                        BarData barData = new BarData(incomeDataSet, expenseDataSet);
                        barData.setBarWidth(0.3f);

                        requireActivity().runOnUiThread(() -> {
                            barChart.setData(barData);
                            barChart.groupBars(0, 0.4f, 0.1f);
                            barChart.invalidate();
                        });
                    }
                });
            }
        });
    }


    private void setupPieChart() {
        LiveData<Integer> liveDataPemasukan = databaseDao.getTotalPemasukan();
        LiveData<Integer> liveDataPengeluaran = databaseDao.getTotalPengeluaran();

        liveDataPemasukan.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer totalPemasukan) {
                liveDataPengeluaran.observe(getViewLifecycleOwner(), new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer totalPengeluaran) {
                        int income = totalPemasukan != null ? totalPemasukan : 0;
                        int expense = totalPengeluaran != null ? totalPengeluaran : 0;

                        List<PieEntry> pieEntries = new ArrayList<>();
                        pieEntries.add(new PieEntry(income, "Pendapatan"));
                        pieEntries.add(new PieEntry(expense, "Pengeluaran"));

                        PieDataSet dataSet = new PieDataSet(pieEntries, "Keuangan");
                        dataSet.setColors(Color.GREEN, Color.RED); // Set colors for Pendapatan and Pengeluaran
                        PieData pieData = new PieData(dataSet);
                        pieData.setValueFormatter(new PercentFormatter(pieChart)); // Format percentage

                        requireActivity().runOnUiThread(() -> {
                            pieChart.setData(pieData);
                            pieChart.invalidate();
                        });
                    }
                });
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }
}
