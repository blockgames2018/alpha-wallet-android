package com.alphawallet.app.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.alphawallet.app.entity.TokenLocator;
import com.alphawallet.app.service.AssetDefinitionService;
import com.alphawallet.app.service.TokensService;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TokenScriptManagementViewModel extends BaseViewModel {

    private final AssetDefinitionService assetDefinitionService;
    private final MutableLiveData<List<TokenLocator>> tokenLocatorsLiveData;

    public TokenScriptManagementViewModel(AssetDefinitionService assetDefinitionService) {
        this.assetDefinitionService = assetDefinitionService;
        tokenLocatorsLiveData = new MutableLiveData<>();

        assetDefinitionService.getAllTokenDefinitions() //holds for loading complete then returns origin contracts
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::addUnresolvedTokens)
                .isDisposed();
    }

    private void addUnresolvedTokens(List<TokenLocator> tokenLocators) {
        tokenLocatorsLiveData.setValue(tokenLocators);
    }

    public MutableLiveData<List<TokenLocator>> getTokenLocatorsLiveData() {
        return tokenLocatorsLiveData;
    }

    public AssetDefinitionService getAssetService()
    {
        return assetDefinitionService;
    }
}
