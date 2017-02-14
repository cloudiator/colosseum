package dtos.conversion.converters;

import com.google.inject.TypeLiteral;

import java.util.List;
import java.util.Map;

import dtos.api.Dto;
import dtos.conversion.AbstractConverter;
import dtos.conversion.transformers.IdToModelTransformer;
import dtos.conversion.transformers.Transformer;
import dtos.generic.KeyValue;
import dtos.generic.KeyValues;
import de.uniulm.omi.cloudiator.persistance.entities.ApplicationComponent;
import de.uniulm.omi.cloudiator.persistance.entities.Model;
import de.uniulm.omi.cloudiator.persistance.repositories.ModelService;

/**
 * Created by Frank on 18.07.2016.
 */
public abstract class ComponentHorizontalScalingActionConverter <T extends Model, U extends Dto> extends
        AbstractConverter<T, U> {
    public ComponentHorizontalScalingActionConverter(Class<T> t, Class<U> s, ModelService<ApplicationComponent> applicationComponentModelService) {
        super(t, s);
        this.applicationComponentModelService = applicationComponentModelService;
    }

    private final ModelService<ApplicationComponent> applicationComponentModelService;

    @Override public void configure() {
        binding().fromField("amount").toField("amount");
        binding().fromField("min").toField("min");
        binding().fromField("max").toField("max");
        binding().fromField("count").toField("count");
        binding(Long.class, ApplicationComponent.class).fromField("applicationComponent").toField("applicationComponent")
                .withTransformation(new IdToModelTransformer<>(applicationComponentModelService));

        binding(new TypeLiteral<List<KeyValue>>() {
        }, new TypeLiteral<Map<String, String>>() {
        }).fromField("externalReferences").toField("externalReferences").withTransformation(
                new Transformer<List<KeyValue>, Map<String, String>>() {
                    @Override public Map<String, String> transform(List<KeyValue> tags) {
                        return KeyValues.to(tags);
                    }

                    @Override public List<KeyValue> transformReverse(
                            Map<String, String> stringStringMap) {
                        return KeyValues.of(stringStringMap);
                    }
                });
    }
}
