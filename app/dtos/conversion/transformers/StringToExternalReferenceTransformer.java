package dtos.conversion.transformers;

import com.google.inject.Inject;
import models.generic.ExternalReference;
import models.service.api.generic.ModelService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by daniel on 03.06.15.
 */
public class StringToExternalReferenceTransformer
    implements Transformer<List<String>, List<ExternalReference>> {

    private final ModelService<ExternalReference> externalReferenceModelService;

    @Inject public StringToExternalReferenceTransformer(
        ModelService<ExternalReference> externalReferenceModelService) {
        this.externalReferenceModelService = externalReferenceModelService;
    }

    @Override public List<ExternalReference> transform(List<String> strings) {
        List<ExternalReference> externalReferences = new ArrayList<>(strings.size());
        for (String s : strings) {
            ExternalReference externalReference = new ExternalReference(s);
            externalReferenceModelService.save(externalReference);
            externalReferences.add(externalReference);
        }
        return externalReferences;
    }

    @Override public List<String> transformReverse(List<ExternalReference> externalReferences) {
        List<String> strings = new ArrayList<>(externalReferences.size());
        strings.addAll(externalReferences.stream().map(ExternalReference::getReference)
            .collect(Collectors.toList()));
        return strings;
    }
}
