package ru.divizdev.homefinance.presentation.about

import android.app.Dialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import mehdi.sakout.aboutpage.AboutPage
import mehdi.sakout.aboutpage.Element
import ru.divizdev.homefinance.R

class AboutDialog : DialogFragment() {

    private val gitHub = "divizdev/HomeFinance"
    private val website = "http://divizdev.ru"
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        var version = "1.0"
        try {
            val pInfo = activity!!.packageManager.getPackageInfo(activity!!.packageName, 0)
            version = "${context!!.resources.getString(R.string.title_version)}: ${pInfo.versionName}"
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        val aboutPage = AboutPage(context)
                .setDescription(context!!.resources.getString(R.string.about_description))
                .addItem(Element().setTitle(version))
                .addWebsite(website)
                .addGitHub(gitHub)
                .create()


        return AlertDialog.Builder(activity!!)
                .setTitle(R.string.title_dialog_about)
                .setView(aboutPage)
                .setPositiveButton(android.R.string.ok, { dialog, _ -> dialog.dismiss() })
                .create()

    }
}