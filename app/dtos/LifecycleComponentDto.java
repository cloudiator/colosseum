package dtos;

import dtos.generic.impl.NamedDto;

/**
 * Created by daniel seybold on 16.12.2014.
 */
public class LifecycleComponentDto extends NamedDto {


    public String download;

    public String install;

    public String start;

    public String stop;

    public LifecycleComponentDto(){

    }
    
    public LifecycleComponentDto(String name, String download, String install, String start, String stop){
        super(name);
        this.download = download;
        this.install = install;
        this.start = start;
        this.stop = stop;
    }


}
