package net.yested.ext.sweetalert

public data class SwalOptions(

        /**
         * 	The title of the modal. It can either be added to the object under the key "title" or passed as the first parameter of the function.
         */
        val title:String,

        /**
         * A description for the modal. It can either be added to the object under the key "text" or passed as the second parameter of the function.
         */
        val text:String? = null,

        /**
         * The type of the modal. SweetAlert comes with 4 built-in types which will show a corresponding icon animation: "warning", "error", "success" and "info". It can either be put in the array under the key "type" or passed as the third parameter of the function.
         */
        val type:String? = null,

        /**
         * If set to true, the user can dismiss the modal by clicking outside it.
         */
        val allowOutsideClick:Boolean = false,

        /**
         * Use this to change the text on the "Confirm"-button. If showCancelButton is set as true, the confirm button will automatically show "Confirm" instead of "OK".
         */
        val showCancelButton:Boolean = false,

        /**
         * Use this to change the background color of the "Confirm"-button (must be a HEX value).
         */
        val confirmButtonText:String = "OK",

        /**
         *
         */
        val confirmButtonColor:String = "#AEDEF4",

        /**
         * Use this to change the text on the "Cancel"-button.
         */
        val cancelButtonText:String = "Cancel",

        /**
         * Set to false if you want the modal to stay open even if the user presses the "Confirm"-button. This is especially useful if the function attached to the "Confirm"-button is another SweetAlert.
         */
        val closeOnConfirm:Boolean = true,

        val closeOnCancel:Boolean = true,

        /**
         * Add a customized icon for the modal. Should contain a string with the path to the image.
         */
        val imageUrl:String? = null,

        /**
         * If imageUrl is set, you can specify imageSize to describes how big you want the icon to be in px. Pass in a string with two values separated by an "x". The first value is the width, the second is the height.
         */
        val imageSize:String? = "80x80",

        val timer: Number? = null
)

@native("swal") public fun swal(option:SwalOptions, handler:Function1<Boolean?, Unit>? = null):Unit = null!!
