import React from 'react';

const RenderInputField = ({ field, formik }) => {
  return (
    <div className='form-group col-md-3'>
      <label>{field}:</label>
      <input 
        type='text' 
        name={field} 
        onChange={formik.handleChange}
        onBlur={formik.handleBlur}
        value={formik.values[field]}
        required />
      {formik.errors[field] && formik.touched[field] && (
        <div className="eu-error">{formik.errors[field]}</div>
      )}
    </div>
  );
}

export default RenderInputField;
