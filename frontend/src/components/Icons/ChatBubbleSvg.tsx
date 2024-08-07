interface ChatBubbleSvgProps {
  color?: string;
}

export default function ChatBubbleSvg(props: ChatBubbleSvgProps) {
  const { color } = props;

  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      width="15"
      height="15"
      viewBox="0 0 15 15"
      fill="none"
    >
      <path
        d="M4.04271 15C4.31479 15 4.50558 14.8557 4.82529 14.5373L7.28889 12.1519L11.8827 12.159C13.9111 12.1664 15 10.9662 15 8.84819V3.31078C15 1.19273 13.9111 0 11.883 0H3.11697C1.0959 0 0 1.18533 0 3.31078V8.84819C0 10.9733 1.09561 12.159 3.11697 12.1519H3.43697V14.2554C3.43697 14.7036 3.66143 15 4.04271 15Z"
        fill={color || '#BAEBFF'}
      />
    </svg>
  );
}
