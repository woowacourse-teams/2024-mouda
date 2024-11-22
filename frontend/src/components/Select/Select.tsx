import { PropsWithChildren } from 'react';
import { useTheme } from '@emotion/react';
import * as S from './Select.style';

interface Option {
  value: string;
  label: string;
}

interface SelectProps {
  options: Option[];
  value: string;
  onChange: (value: string) => void;
  placeholder?: string;
  disabled?: boolean;
}

export default function Select(props: PropsWithChildren<SelectProps>) {
  const { options, value, onChange, placeholder, disabled } = props;

  const handleChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    onChange(e.target.value);
  };

  const theme = useTheme();

  return (
    <select
      css={S.select({ theme })}
      value={value}
      onChange={handleChange}
      disabled={disabled}
    >
      {placeholder && (
        <option value="" disabled>
          {placeholder}
        </option>
      )}

      {options.map((option) => (
        <option key={option.value} value={option.value}>
          {option.label}
        </option>
      ))}
    </select>
  );
}
