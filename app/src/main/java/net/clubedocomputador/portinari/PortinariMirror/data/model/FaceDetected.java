package net.clubedocomputador.portinari.PortinariMirror.data.model;

import org.greenrobot.eventbus.EventBus;

public class FaceDetected {
    private final byte[] face;

    public FaceDetected(byte[] face) {
        this.face = face;
        EventBus.getDefault().post(this);
    }

    public byte[] getFace() {
        return face;
    }
}
