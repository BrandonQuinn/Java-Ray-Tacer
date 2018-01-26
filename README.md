## Features

- Supported volumes: spheres, infinite plane, tris, polygon (ONLY CONVEX POLYGONS)
	- It only has a simply polygon triangulation algorithm that can not handle concave polys
- Multiple point lights
- Point lights (inverse square intensity w/ quadratic attenutation)
- Orthographic camera
- Multi-threaded rendering (CPU)
- FSAA (Fullscreen anti-aliasing)
- Quad and tri hit detection

## TODO

### Viewing/Camera

- Perspective-correct interpolation
- Camera/frustum rotation
- World space to screen space transformation

#### Lighting

- Ambient lighting
- Point light variability (intensity, range)
- Specular/reflections lighting
- Caustics/refraction
- Luminance surface

#### Compute

- OpenCL hardware acceleration
- Distributed rendering

#### Data Representation

- Collada support

## Noteworthy Issues

- View frustum doesn't work at all, it's just a straight line projection
- Model loading at least from Blender leave models grossly undersized and mis-located

## Dependencies 

- JOML-1.9.7 (OpenGL 3D Mathematics Library)

## Current screenshots

![Sorry no image!](https://i.imgur.com/FZ31gZj.png)
![Sorry no image!](https://i.imgur.com/22EYPiO.png)