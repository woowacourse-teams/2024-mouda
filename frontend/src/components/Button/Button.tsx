import type ButtonProps from './Button.type';
import { shapes } from './Button.style';

export default function Button(props: ButtonProps) {
  const { shape, onClick, children } = props;
  return (
    <button css={shapes[shape]} onClick={onClick}>
      {children}
    </button>
  );
}
