package ru.divizdev.homefinance.presentation.statistics.view

import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.fragment_statistics.*
import ru.divizdev.homefinance.R
import ru.divizdev.homefinance.di.Factory
import ru.divizdev.homefinance.entities.CategoryType
import ru.divizdev.homefinance.entities.OperationStatistics
import ru.divizdev.homefinance.mvp.BaseMvpActivity
import ru.divizdev.homefinance.mvp.BaseMvpFragment
import ru.divizdev.homefinance.presentation.main.presenter.AbstractMainPresenter
import ru.divizdev.homefinance.presentation.main.view.IMainView
import ru.divizdev.homefinance.presentation.statistics.presenter.AbstractStatisticsPresenter
import java.util.*

class StatisticsFragment : BaseMvpFragment<AbstractStatisticsPresenter, IStatisticsView, IMainView, AbstractMainPresenter>(),
        IStatisticsView {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.fragment_statistics, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.loadWallets()

        histogram.xAxis.isEnabled = false
        histogram.description.isEnabled = false
        initDatesFilters()
        operation_type_spinner.adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item,
                CategoryType.values().map { type -> getString(type.stringId) })

        search_button.setOnClickListener { loadStatistics() }
    }

    override fun getInstancePresenter(): AbstractStatisticsPresenter {
        return Factory.getStatisticsPresenter((requireContext()
                as BaseMvpActivity<AbstractMainPresenter, IMainView>).presenter)
    }

    override fun getMvpView(): IStatisticsView {
        return this
    }

    override fun showWallets(wallets: List<String>) {
        wallet_spinner.adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, wallets)
    }

    override fun showStatistics(statisticsValues: List<OperationStatistics>) {
        var order = 0f
        val statisticsDataSet = BarDataSet(statisticsValues.map { BarEntry(order++, it.amount.toFloat()) }, null)
        statisticsDataSet.colors = ColorTemplate.JOYFUL_COLORS.toList()
        histogram.data = BarData(statisticsDataSet)
        histogram.notifyDataSetChanged()
        histogram.invalidate()
    }

    private fun loadStatistics() {
        presenter.loadStatistics(CategoryType.values()[operation_type_spinner.selectedItemPosition],
                wallet_spinner.selectedItemPosition, date_from_input_edit_text.date.time, date_to_input_edit_text.date.time)
    }

    private fun initDatesFilters() {
        val dateFrom = Calendar.getInstance()
        dateFrom.add(Calendar.DATE, -1)
        date_from_input_edit_text.manager = fragmentManager
        date_from_input_edit_text.setDateFormat(DateFormat.getDateFormat(context))
        date_from_input_edit_text.date = dateFrom
        date_from_input_edit_text.setText(DateFormat.getDateFormat(context).format(dateFrom.time))

        date_to_input_edit_text.manager = fragmentManager
        date_to_input_edit_text.setDateFormat(DateFormat.getDateFormat(context))
        date_to_input_edit_text.date = Calendar.getInstance()
        date_to_input_edit_text.setText(DateFormat.getDateFormat(context).format(Calendar.getInstance().time))
    }
}