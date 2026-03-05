package com.example.airport05;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class VooPagerAdapter extends FragmentPagerAdapter {

    private final VooFragment chegadasFragment;
    private final VooFragment partidasFragment;

    public VooPagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        chegadasFragment = VooFragment.newInstance("Chegada");
        partidasFragment = VooFragment.newInstance("Partida");
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return position == 0 ? chegadasFragment : partidasFragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return position == 0 ? "Chegadas" : "Partidas";
    }

    public VooFragment getChegadasFragment() {
        return chegadasFragment;
    }

    public VooFragment getPartidasFragment() {
        return partidasFragment;
    }
}
