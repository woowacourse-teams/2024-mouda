import MoimInputProps from './MoimInput.type';
import * as S from './MoimInput.style';

export default function MoimInput(props: MoimInputProps) {
  const { title, type, placeholder, required } = props.data;
  return (
    <label htmlFor={title}>
      <h3 css={S.title}>
        {title}
        <span css={S.required}>{required ? '*' : ''}</span>
      </h3>

      <input
        css={S.input}
        type={type}
        placeholder={placeholder}
        id={title}
      ></input>
    </label>
  );
}
