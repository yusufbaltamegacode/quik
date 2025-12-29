/*
 * Copyright (C) 2017 Moez Bhatti <moez.bhatti@gmail.com>
 *
 * This file is part of QKSMS.
 *
 * QKSMS is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * QKSMS is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with QKSMS.  If not, see <http://www.gnu.org/licenses/>.
 */
package dev.megacode.quik.feature.scheduled

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.megacode.quik.R
import dev.megacode.quik.common.base.QkAdapter
import dev.megacode.quik.common.base.QkViewHolder
import dev.megacode.quik.extensions.getName
import dev.megacode.quik.feature.extensions.LoadBestIconIntoImageView
import dev.megacode.quik.feature.extensions.loadBestIconIntoImageView
import kotlinx.android.synthetic.main.scheduled_message_image_list_item.fileName
import kotlinx.android.synthetic.main.scheduled_message_image_list_item.thumbnail
import javax.inject.Inject


class ScheduledMessageAttachmentAdapter @Inject constructor(
    private val context: Context
) : QkAdapter<Uri, QkViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QkViewHolder {
        return QkViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.scheduled_message_image_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: QkViewHolder, position: Int) {
        val uri = getItem(position)

        // set best image and text to use for icon
        when (getItem(position).loadBestIconIntoImageView(context, holder.thumbnail)) {
            LoadBestIconIntoImageView.Missing -> {
                holder.fileName.text = context.getString(R.string.attachment_missing)
                holder.fileName.visibility = View.VISIBLE
            }
            LoadBestIconIntoImageView.ActivityIcon,
            LoadBestIconIntoImageView.DefaultAudioIcon,
            LoadBestIconIntoImageView.GenericIcon -> {
                // generic style icon used, also show name
                holder.fileName.text = uri.getName(context)
                holder.fileName.visibility = View.VISIBLE
            }
            else -> holder.fileName.visibility = View.GONE
        }
    }
}
