package org.example.model.enums;

import org.example.fragment.message.DeleteConfirmationFragment;
import org.example.fragment.message.UpperSmallMessageFragment;
import org.jacpfx.rcp.components.managedFragment.ManagedFragment;


public enum MessageView {
    UPPER_SMALL_MESSAGE(new UpperSmallMessageFragment()), MIDDLE_BIG_MESSAGE(new DeleteConfirmationFragment());
    private ManagedFragment managedFragment;

    MessageView(ManagedFragment managedFragment) {
        this.managedFragment = managedFragment;
    }
    public ManagedFragment getManagementFragment() {return managedFragment; }
}
