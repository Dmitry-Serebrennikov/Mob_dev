package com.argent.azuretranslator;

import androidx.annotation.NonNull;

public class Language {
    String name;
    String nativeName;

    public Language(Language language){
        this.name = language.name;
        this.nativeName = language.nativeName;
    }

    @NonNull
    @Override
    public String toString() {
        return nativeName;
    }
}
