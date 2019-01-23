package com.cloudcomputing.docker.limits.model.stats;

import org.junit.Test;

import static de.xn__ho_hia.storage_unit.StorageUnits.bytes;
import static de.xn__ho_hia.storage_unit.StorageUnits.megabyte;
import static org.assertj.core.api.Assertions.assertThat;

public class ResourceDescriptorTest {

    @Test
    public void initial() {
        assertThat(ResourceDescriptor.initial().getBlkio_weight()).isEqualTo(0);
        assertThat(ResourceDescriptor.initial().getCpu_shares()).isEqualTo(0);
        assertThat(ResourceDescriptor.initial().getMem_limit().longValue()).isEqualTo(0);
    }

    @Test
    public void newNegative() {
        final ResourceDescriptor resourceDescriptor = new ResourceDescriptor("-60M", -10, -50);
        assertThat(resourceDescriptor.getMem_limit()).isEqualTo(megabyte(-60));
        assertThat(resourceDescriptor.getCpu_shares()).isEqualTo(-10);
        assertThat(resourceDescriptor.getBlkio_weight().longValue()).isEqualTo(-50);
    }

    @Test
    public void add() {
        ResourceDescriptor a,b;
        a = new ResourceDescriptor("60M", 10, 50);
        b = new ResourceDescriptor("20M", 20, 70);
        final ResourceDescriptor result = a.add(b);
        assertThat(result).isEqualTo(new ResourceDescriptor("80M", 30, 120));
    }

    @Test
    public void subtract() {
        ResourceDescriptor a,b;
        a = new ResourceDescriptor("80M", 30, 120);
        b = new ResourceDescriptor("20M", 20, 70);
        final ResourceDescriptor result = a.subtract(b);
        assertThat(result).isEqualTo(new ResourceDescriptor("60M", 10, 50));
    }

    @Test
    public void subtractNegative() {
        ResourceDescriptor a,b;
        a = new ResourceDescriptor("-80M", -30, -120);
        b = new ResourceDescriptor("20M", 20, 70);
        final ResourceDescriptor result = a.subtract(b);
        assertThat(result).isEqualTo(new ResourceDescriptor("-100M", -50, -190));
    }

    @Test
    public void subtractNegative2() {
        ResourceDescriptor a,b;
        a = new ResourceDescriptor("80M", 30, 120);
        b = new ResourceDescriptor("-20M", -20, -70);
        final ResourceDescriptor result = a.subtract(b);
        assertThat(result).isEqualTo(new ResourceDescriptor("100M", 50, 190));
    }

    @Test
    public void getMem_limit() {
        assertThat(new ResourceDescriptor("60M", 10, 50).getMem_limit()).isEqualTo(bytes(ResourceDescriptor.toBytes("60M)")));
    }

    @Test
    public void getCpu_shares() {
        assertThat(new ResourceDescriptor("60M", 10, 50).getCpu_shares()).isEqualTo(10);
    }

    @Test
    public void getBlkio_weight() {
        assertThat(new ResourceDescriptor("60M", 10, 50).getBlkio_weight()).isEqualTo(50);
    }

    @Test
    public void toBytes() {
        assertThat(ResourceDescriptor.toBytes("20")).isEqualTo(20L);
    }

    @Test
    public void toBytesBytes() {
        assertThat(ResourceDescriptor.toBytes("20 B")).isEqualTo(20L);
    }

    @Test
    public void toBytesKiloBytes() {
        assertThat(ResourceDescriptor.toBytes("20 KB")).isEqualTo(20_000L);
    }

    @Test
    public void toBytesMegabyte() {
        assertThat(ResourceDescriptor.toBytes("20M")).isEqualTo(20_000_000L);
    }

    @Test
    public void toBytesGigaBytes() {
        assertThat(ResourceDescriptor.toBytes("20 GB")).isEqualTo(20_000_000_000L);
    }

    @Test
    public void toBytesNegative() {
        assertThat(ResourceDescriptor.toBytes("-20 B")).isEqualTo(-20L);
    }
}