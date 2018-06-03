package net.clubedocomputador.portinari.injection.component;

import dagger.Subcomponent;
import net.clubedocomputador.portinari.injection.PerFragment;
import net.clubedocomputador.portinari.injection.module.FragmentModule;

/**
 * This component inject dependencies to all Fragments across the application
 */
@PerFragment
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {

    //void inject(SomeFragment someFragment);

}
