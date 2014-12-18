package dtos;

import dtos.generic.impl.NamedDto;

/**
 * Created by daniel seybold on 16.12.2014.
 */
public class ApplicationDto  extends NamedDto{

    public ApplicationDto(){
        super();
    }

    public ApplicationDto(String name){
        super(name);
    }
}
