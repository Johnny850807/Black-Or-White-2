package com.pokewords.framework.commons.bundles;

/**
 * A read only version of bundle.
 * @author johnny850807 (waterball)
 */
public class ReadOnlyBundle extends Bundle {

    protected ReadOnlyBundle() { }

    protected ReadOnlyBundle(Bundle bundle) {
        super(bundle.getEventId());
        this.data = bundle.data;
    }


    @Override
    public void put(Object key, Object value) {
        throw new UnsupportedOperationException("The bundle is read-only.");
    }

    public static class Builder {
        private Bundle bundle;

        public Builder() {
            this.bundle = new Bundle();
        }

        public Builder(Bundle bundle) {
            this.bundle = new Bundle();
            this.bundle.data = bundle.data;
        }

        public Builder eventId(int eventId) {
            bundle.eventId = eventId;
            return this;
        }

        public Builder put(Object key, Object value) {
            bundle.put(key, value);
            return this;
        }

        public ReadOnlyBundle build() {
            return new ReadOnlyBundle(bundle);
        }
    }
}
