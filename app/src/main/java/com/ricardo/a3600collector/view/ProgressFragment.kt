package com.ricardo.a3600collector.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.res.ResourcesCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ricardo.a3600collector.R
import kotlinx.android.synthetic.main.fragment_progress.view.*

private const val ARG_PROGRESS = "prog"

class ProgressFragment : Fragment() {
    private var progress: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            progress = it.getInt(ARG_PROGRESS)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_progress, container, false)
        rootView.progress_text.typeface = ResourcesCompat.getFont(context!!, R.font.montserrat_regular)
        rootView.progress_text.text = progress.toString()
        rootView.progress_circular.setProgress(progress!!.toFloat())
        rootView.progress_circular.setMax(3600)
        return rootView
    }

    companion object {
        @JvmStatic
        fun newInstance(progress: Int) =
                ProgressFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_PROGRESS, progress)
                    }
                }
    }
}
