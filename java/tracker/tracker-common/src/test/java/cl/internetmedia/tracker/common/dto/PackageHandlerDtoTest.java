package cl.internetmedia.tracker.common.dto;

import static org.junit.Assert.*;
import org.junit.Test;

import cl.internetmedia.tracker.common.dto.PackageHandlerDto;

public class PackageHandlerDtoTest {
  private PackageHandlerDto pkgHandler;

  @Test
  public void testInstanceNotNull() {
    pkgHandler = new PackageHandlerDto(1, "John", "Doe", "555-45678");
    assertNotNull(pkgHandler);
  }

}
