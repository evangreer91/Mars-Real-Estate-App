/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.android.marsrealestate

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

// binding adapter takes the URL from an XML attribute associated with an ImgView
// Uses Glide to load the image

// this tells data binding that we want this binding adapter executed when an XML item
// has the imageUrl attribute
@BindingAdapter("imageUrl")
// the view parameter is specified as an image view
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        // convert image url to a uri
        // make sure the resulting uri has the https scheme
        val imgUri = it.toUri().buildUpon().scheme("https").build()

        // Glide has a fluent interface
        // to load an image with Glide
        // pass in an android context for the img view
        Glide.with(imgView.context)
            .load(imgUri)
            // Glide can help us improve the user experience by showing
            // a placeholder image while loading the image and an error image if the loading fails
            // Glide adds these objects to the request using a RequestOptions object
            .apply(
                RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image))
            .into(imgView)

    }

}
