// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: employer.proto

package org.example;

public interface EmployerOrBuilder extends
    // @@protoc_insertion_point(interface_extends:org.example.Employer)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>int64 id = 1;</code>
   */
  long getId();

  /**
   * <code>string type = 2;</code>
   */
  java.lang.String getType();
  /**
   * <code>string type = 2;</code>
   */
  com.google.protobuf.ByteString
      getTypeBytes();

  /**
   * <code>string number = 3;</code>
   */
  java.lang.String getNumber();
  /**
   * <code>string number = 3;</code>
   */
  com.google.protobuf.ByteString
      getNumberBytes();

  /**
   * <code>string name = 4;</code>
   */
  java.lang.String getName();
  /**
   * <code>string name = 4;</code>
   */
  com.google.protobuf.ByteString
      getNameBytes();

  /**
   * <code>string numberIDE = 5;</code>
   */
  java.lang.String getNumberIDE();
  /**
   * <code>string numberIDE = 5;</code>
   */
  com.google.protobuf.ByteString
      getNumberIDEBytes();

  /**
   * <code>string dateAffiliation = 6;</code>
   */
  java.lang.String getDateAffiliation();
  /**
   * <code>string dateAffiliation = 6;</code>
   */
  com.google.protobuf.ByteString
      getDateAffiliationBytes();

  /**
   * <code>string dateRadiation = 7;</code>
   */
  java.lang.String getDateRadiation();
  /**
   * <code>string dateRadiation = 7;</code>
   */
  com.google.protobuf.ByteString
      getDateRadiationBytes();

  /**
   * <code>repeated .org.example.Salary salaries = 8;</code>
   */
  java.util.List<org.example.Salary> 
      getSalariesList();
  /**
   * <code>repeated .org.example.Salary salaries = 8;</code>
   */
  org.example.Salary getSalaries(int index);
  /**
   * <code>repeated .org.example.Salary salaries = 8;</code>
   */
  int getSalariesCount();
  /**
   * <code>repeated .org.example.Salary salaries = 8;</code>
   */
  java.util.List<? extends org.example.SalaryOrBuilder> 
      getSalariesOrBuilderList();
  /**
   * <code>repeated .org.example.Salary salaries = 8;</code>
   */
  org.example.SalaryOrBuilder getSalariesOrBuilder(
      int index);
}