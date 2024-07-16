import MoimInputProps from './MoimInput.type';
import * as S from './MoimInput.style';

export default function MoimInput(props: MoimInputProps) {
  const {
    name,
    data: { title, type, placeholder, required },
    onChange,
  } = props;

  return (
    <label htmlFor={title}>
      <h3 css={S.title}>
        {title}
        <span css={S.required}>{required ? '*' : ''}</span>
      </h3>

      <input
        name={name}
        css={S.input}
        type={type}
        placeholder={placeholder}
        id={title}
        onChange={onChange}
      ></input>
    </label>
  );
}
