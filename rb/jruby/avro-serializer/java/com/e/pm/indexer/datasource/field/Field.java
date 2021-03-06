/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package com.e.pm.indexer.datasource.field;
@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class Field extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"Field\",\"namespace\":\"com.e.pm.indexer.datasource.field\",\"fields\":[{\"name\":\"order\",\"type\":\"int\"},{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"alias\",\"type\":\"string\"},{\"name\":\"value\",\"type\":\"string\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
  @Deprecated public int order;
  @Deprecated public java.lang.CharSequence name;
  @Deprecated public java.lang.CharSequence alias;
  @Deprecated public java.lang.CharSequence value;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use {@link \#newBuilder()}. 
   */
  public Field() {}

  /**
   * All-args constructor.
   */
  public Field(java.lang.Integer order, java.lang.CharSequence name, java.lang.CharSequence alias, java.lang.CharSequence value) {
    this.order = order;
    this.name = name;
    this.alias = alias;
    this.value = value;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return order;
    case 1: return name;
    case 2: return alias;
    case 3: return value;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: order = (java.lang.Integer)value$; break;
    case 1: name = (java.lang.CharSequence)value$; break;
    case 2: alias = (java.lang.CharSequence)value$; break;
    case 3: value = (java.lang.CharSequence)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'order' field.
   */
  public java.lang.Integer getOrder() {
    return order;
  }

  /**
   * Sets the value of the 'order' field.
   * @param value the value to set.
   */
  public void setOrder(java.lang.Integer value) {
    this.order = value;
  }

  /**
   * Gets the value of the 'name' field.
   */
  public java.lang.CharSequence getName() {
    return name;
  }

  /**
   * Sets the value of the 'name' field.
   * @param value the value to set.
   */
  public void setName(java.lang.CharSequence value) {
    this.name = value;
  }

  /**
   * Gets the value of the 'alias' field.
   */
  public java.lang.CharSequence getAlias() {
    return alias;
  }

  /**
   * Sets the value of the 'alias' field.
   * @param value the value to set.
   */
  public void setAlias(java.lang.CharSequence value) {
    this.alias = value;
  }

  /**
   * Gets the value of the 'value' field.
   */
  public java.lang.CharSequence getValue() {
    return value;
  }

  /**
   * Sets the value of the 'value' field.
   * @param value the value to set.
   */
  public void setValue(java.lang.CharSequence value) {
    this.value = value;
  }

  /** Creates a new Field RecordBuilder */
  public static com.e.pm.indexer.datasource.field.Field.Builder newBuilder() {
    return new com.e.pm.indexer.datasource.field.Field.Builder();
  }
  
  /** Creates a new Field RecordBuilder by copying an existing Builder */
  public static com.e.pm.indexer.datasource.field.Field.Builder newBuilder(com.e.pm.indexer.datasource.field.Field.Builder other) {
    return new com.e.pm.indexer.datasource.field.Field.Builder(other);
  }
  
  /** Creates a new Field RecordBuilder by copying an existing Field instance */
  public static com.e.pm.indexer.datasource.field.Field.Builder newBuilder(com.e.pm.indexer.datasource.field.Field other) {
    return new com.e.pm.indexer.datasource.field.Field.Builder(other);
  }
  
  /**
   * RecordBuilder for Field instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<Field>
    implements org.apache.avro.data.RecordBuilder<Field> {

    private int order;
    private java.lang.CharSequence name;
    private java.lang.CharSequence alias;
    private java.lang.CharSequence value;

    /** Creates a new Builder */
    private Builder() {
      super(com.e.pm.indexer.datasource.field.Field.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(com.e.pm.indexer.datasource.field.Field.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.order)) {
        this.order = data().deepCopy(fields()[0].schema(), other.order);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.name)) {
        this.name = data().deepCopy(fields()[1].schema(), other.name);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.alias)) {
        this.alias = data().deepCopy(fields()[2].schema(), other.alias);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.value)) {
        this.value = data().deepCopy(fields()[3].schema(), other.value);
        fieldSetFlags()[3] = true;
      }
    }
    
    /** Creates a Builder by copying an existing Field instance */
    private Builder(com.e.pm.indexer.datasource.field.Field other) {
            super(com.e.pm.indexer.datasource.field.Field.SCHEMA$);
      if (isValidValue(fields()[0], other.order)) {
        this.order = data().deepCopy(fields()[0].schema(), other.order);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.name)) {
        this.name = data().deepCopy(fields()[1].schema(), other.name);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.alias)) {
        this.alias = data().deepCopy(fields()[2].schema(), other.alias);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.value)) {
        this.value = data().deepCopy(fields()[3].schema(), other.value);
        fieldSetFlags()[3] = true;
      }
    }

    /** Gets the value of the 'order' field */
    public java.lang.Integer getOrder() {
      return order;
    }
    
    /** Sets the value of the 'order' field */
    public com.e.pm.indexer.datasource.field.Field.Builder setOrder(int value) {
      validate(fields()[0], value);
      this.order = value;
      fieldSetFlags()[0] = true;
      return this; 
    }
    
    /** Checks whether the 'order' field has been set */
    public boolean hasOrder() {
      return fieldSetFlags()[0];
    }
    
    /** Clears the value of the 'order' field */
    public com.e.pm.indexer.datasource.field.Field.Builder clearOrder() {
      fieldSetFlags()[0] = false;
      return this;
    }

    /** Gets the value of the 'name' field */
    public java.lang.CharSequence getName() {
      return name;
    }
    
    /** Sets the value of the 'name' field */
    public com.e.pm.indexer.datasource.field.Field.Builder setName(java.lang.CharSequence value) {
      validate(fields()[1], value);
      this.name = value;
      fieldSetFlags()[1] = true;
      return this; 
    }
    
    /** Checks whether the 'name' field has been set */
    public boolean hasName() {
      return fieldSetFlags()[1];
    }
    
    /** Clears the value of the 'name' field */
    public com.e.pm.indexer.datasource.field.Field.Builder clearName() {
      name = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /** Gets the value of the 'alias' field */
    public java.lang.CharSequence getAlias() {
      return alias;
    }
    
    /** Sets the value of the 'alias' field */
    public com.e.pm.indexer.datasource.field.Field.Builder setAlias(java.lang.CharSequence value) {
      validate(fields()[2], value);
      this.alias = value;
      fieldSetFlags()[2] = true;
      return this; 
    }
    
    /** Checks whether the 'alias' field has been set */
    public boolean hasAlias() {
      return fieldSetFlags()[2];
    }
    
    /** Clears the value of the 'alias' field */
    public com.e.pm.indexer.datasource.field.Field.Builder clearAlias() {
      alias = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /** Gets the value of the 'value' field */
    public java.lang.CharSequence getValue() {
      return value;
    }
    
    /** Sets the value of the 'value' field */
    public com.e.pm.indexer.datasource.field.Field.Builder setValue(java.lang.CharSequence value) {
      validate(fields()[3], value);
      this.value = value;
      fieldSetFlags()[3] = true;
      return this; 
    }
    
    /** Checks whether the 'value' field has been set */
    public boolean hasValue() {
      return fieldSetFlags()[3];
    }
    
    /** Clears the value of the 'value' field */
    public com.e.pm.indexer.datasource.field.Field.Builder clearValue() {
      value = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    @Override
    public Field build() {
      try {
        Field record = new Field();
        record.order = fieldSetFlags()[0] ? this.order : (java.lang.Integer) defaultValue(fields()[0]);
        record.name = fieldSetFlags()[1] ? this.name : (java.lang.CharSequence) defaultValue(fields()[1]);
        record.alias = fieldSetFlags()[2] ? this.alias : (java.lang.CharSequence) defaultValue(fields()[2]);
        record.value = fieldSetFlags()[3] ? this.value : (java.lang.CharSequence) defaultValue(fields()[3]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}
