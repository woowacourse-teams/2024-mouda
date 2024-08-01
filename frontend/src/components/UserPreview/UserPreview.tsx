import { preview } from './UserPreview.style';

interface UserPreviewProps {
  imageUrl?: string;
  size?: string;
  hasCrown?: boolean;
}

export default function UserPreview(props: UserPreviewProps) {
  const { imageUrl, size, hasCrown = false } = props;
  hasCrown;
  return <div css={preview({ imageUrl, size })} />;
}
