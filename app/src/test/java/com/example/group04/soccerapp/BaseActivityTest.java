package com.example.group04.soccerapp;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import androidx.appcompat.app.AppCompatDelegate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class BaseActivityTest {

    @Mock
    Context mockedContext;
    @Mock
    Resources mockedResources;
    @Mock
    Configuration mockedConfiguration;

    @Test
    public void isNightModeActive_MODE_NIGHT_YES() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        boolean returnValue = BaseActivity.isNightModeActive(mockedContext);
        assertTrue("Should return true", returnValue);
    }

    @Test
    public void isNightModeActive_MODE_NIGHT_NO() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        boolean returnValue = BaseActivity.isNightModeActive(mockedContext);
        assertFalse("Should return false", returnValue);
    }

    @Test
    public void isNightModeActive_UI_MODE_NIGHT_NO() {
        AppCompatDelegate.setDefaultNightMode(Configuration.UI_MODE_NIGHT_NO);
        mockedConfiguration.uiMode = Configuration.UI_MODE_NIGHT_NO;
        Mockito.when(mockedContext.getResources()).thenReturn(mockedResources);
        Mockito.when(mockedContext.getResources().getConfiguration()).thenReturn(mockedConfiguration);
        boolean returnValue = BaseActivity.isNightModeActive(mockedContext);
        assertFalse("Should return false", returnValue);
    }

    @Test
    public void isNightModeActive_UI_MODE_NIGHT_YES() {
        AppCompatDelegate.setDefaultNightMode(Configuration.UI_MODE_NIGHT_YES);
        mockedConfiguration.uiMode = Configuration.UI_MODE_NIGHT_YES;
        Mockito.when(mockedContext.getResources()).thenReturn(mockedResources);
        Mockito.when(mockedContext.getResources().getConfiguration()).thenReturn(mockedConfiguration);
        boolean returnValue = BaseActivity.isNightModeActive(mockedContext);
        assertTrue("Should return true", returnValue);
    }

    @Test
    public void isNightModeActive_UI_MODE_NIGHT_UNDEFINED() {
        AppCompatDelegate.setDefaultNightMode(Configuration.UI_MODE_NIGHT_UNDEFINED);
        mockedConfiguration.uiMode = Configuration.UI_MODE_NIGHT_UNDEFINED;
        Mockito.when(mockedContext.getResources()).thenReturn(mockedResources);
        Mockito.when(mockedContext.getResources().getConfiguration()).thenReturn(mockedConfiguration);
        boolean returnValue = BaseActivity.isNightModeActive(mockedContext);
        assertFalse("Should return false", returnValue);
    }
}