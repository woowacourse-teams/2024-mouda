import type ButtonProps from './Button.type';
import { shapes } from './Button.style';

export default function Button(props: ButtonProps) {
  const { shape, children } = props;
  return <button css={shapes[shape]}>{children}</button>;
}
