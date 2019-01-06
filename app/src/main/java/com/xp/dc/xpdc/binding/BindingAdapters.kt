/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xp.dc.xpdc.binding

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.example.hongcheng.common.util.ImageLoadUtils

@BindingAdapter("imageUrl")
fun bindImageUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        ImageLoadUtils.bindImageUrl(view, imageUrl)
    }
}

@BindingAdapter("imageUrlForCycle")
fun bindImageUrlForCycle(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        ImageLoadUtils.bindImageUrlForCycle(view, imageUrl)
    }
}

@BindingAdapter("imageUrlForRound")
fun bindImageUrlForRound(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        ImageLoadUtils.bindImageUrlForRound(view, imageUrl)
    }
}
