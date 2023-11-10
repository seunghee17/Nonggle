package com.example.nongglenonggle.presentation.util

import android.view.MotionEvent
import android.widget.EditText
import androidx.core.content.ContextCompat
import com.google.android.play.core.integrity.v
import com.google.firebase.firestore.DocumentReference
import kotlinx.coroutines.tasks.await


fun EditText.setupClearButton(drawableRightId: Int) {
        val drawableRight = ContextCompat.getDrawable(context, drawableRightId)
        setCompoundDrawablesWithIntrinsicBounds(null, null, drawableRight, null)

        val clearButtonStart = this.width - (drawableRight?.bounds?.width() ?: 0)

        this.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= clearButtonStart) {
                    text.clear()
                    v.onTouchEvent(event)
                    return@setOnTouchListener true
                }
            }
            v.onTouchEvent(event)
            false
        }
    }

    fun EditText.showClearButton(drawableRightId: Int) {
        this.setupClearButton(drawableRightId)
    }

    fun EditText.hideClearButton() {
        setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
    }

    suspend inline fun <reified  T> getDataFromReference(documentReference: DocumentReference): T?{
        return try{
            val documentSnapshot = documentReference.get().await()
            documentSnapshot.toObject(T::class.java)
        }catch (e:Exception){
            null
        }
    }

