package com.haja.haja.View.ui.OffersScreen

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.haja.haja.OnItemClick
import com.infovass.lawyerskw.lawyerskw.Utils.ui.SnackAndToastUtil.Companion.makeToast
import kotlinx.android.synthetic.main.offers_fragment.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.haja.haja.R
import com.haja.haja.Service.model.OfferCategoriesData
import com.haja.haja.Service.model.ProductData
import com.infovass.lawyerskw.lawyerskw.Utils.ui.CustomProgressBar
import com.kaopiz.kprogresshud.KProgressHUD
import kotlinx.android.synthetic.main.app_bar_main.*

class OffersFragment : Fragment(), OnItemClick {

    var lastItemPossion = 0
    override fun onClick(
        itemData: OfferCategoriesData,
        position: Int
    ) {
        getOffers(itemData.id!!)
    }

    companion object {
        fun newInstance() = OffersFragment()
    }

    private lateinit var viewModel: OffersViewModel
    private lateinit var offersAdapter: OffiersAdapter
    private var progress: KProgressHUD? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.offers_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        activity?.appBarTitle?.text = resources.getString(R.string.offers)
        activity?.categoriesBarBack?.visibility = View.GONE
        activity?.categoriesBarMenu?.visibility = View.VISIBLE
        activity?.catBarSearch?.visibility = View.GONE

        viewModel = ViewModelProviders.of(this).get(OffersViewModel::class.java)
        progress = CustomProgressBar.showProgressBar(context!!)
        progress?.show()
        val childAdsLayoutManager = LinearLayoutManager(
            context, LinearLayout.HORIZONTAL, false
        )
        offersCategoriesList.layoutManager = childAdsLayoutManager

        val offersLayoutManager = LinearLayoutManager(
            context, RecyclerView.VERTICAL, false
        )
        offersList.layoutManager = offersLayoutManager
        offersAdapter = OffiersAdapter(context!!, this)
        offersList.adapter = offersAdapter

        viewModel.getCategories().observe(this, Observer { categories ->
            progress?.dismiss()
            if (categories != null) {
                if (categories.result == true) {
                    offersCategoriesList.adapter = OffersMainCatAdapter(context!!, categories.data, this)
                    categories.data?.get(0)?.id?.let { getOffers(it) }
                } else
                    makeToast(context!!, categories.errorMesage.toString())
            } else
                makeToast(context!!, resources.getString(R.string.error))

        })
    }

    private fun getOffers(categoryId: Int) {
        offersAdapter.clearList()
        offersAdapter.notifyDataSetChanged()
        progress?.show()
        viewModel.getOfferts(categoryId).observe(this, Observer { offers ->
            progress?.dismiss()
            if (offers != null) {
                if (offers.result == true) {
                    offersAdapter.setNewProducts(offers.data?.data as ArrayList<ProductData?>)
                    offersAdapter.notifyDataSetChanged()
                } else
                    makeToast(context!!, offers.errorMesage.toString())
            } else
                makeToast(context!!, resources.getString(R.string.error))
        })
    }
}
