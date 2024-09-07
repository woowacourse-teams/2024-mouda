import * as S from '@_components/TextArea/LabeledTextArea.style';
import { useTheme } from '@emotion/react';

import { ChangeEvent, HTMLProps, useState } from 'react';

export interface LabeledTextAreaProps extends HTMLProps<HTMLTextAreaElement> {
  title: string;
  validateFun?: (value: string) => boolean;
}

export default function LabeledTextArea(props: LabeledTextAreaProps) {
  const theme = useTheme();
  const { name, title, placeholder, required, onChange, validateFun, ...args } =
    props;
  const [isError, setIsError] = useState(false);
  const handleTextAreaChange = (e: ChangeEvent<HTMLTextAreaElement>) => {
    if (onChange) {
      onChange(e);
    }

    const value = e.currentTarget.value;
    if (validateFun) {
      const isValid = validateFun(value);
      setIsError(!isValid);
    }
  };
  return (
    <label htmlFor={title} css={S.labelWrapper}>
      <h3 css={S.title({ theme })}>
        {title}
        <span css={S.required({ theme })}>{required ? '*' : ''}</span>
      </h3>

      <textarea
        name={name}
        css={S.textArea({ theme })}
        placeholder={placeholder}
        id={title}
        onChange={handleTextAreaChange}
        {...args}
      />
      <span css={S.errorMessage({ theme })}>{isError && placeholder}</span>
    </label>
  );
}
